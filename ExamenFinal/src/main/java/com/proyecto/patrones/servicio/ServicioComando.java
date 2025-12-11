package com.proyecto.patrones.servicio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.patrones.dominio.Pedido;
import com.proyecto.patrones.dominio.PedidoItem;
import com.proyecto.patrones.dto.PedidoCreateDto;
import com.proyecto.patrones.dto.ProductoDto;
import com.proyecto.patrones.patrones.memento.PedidoMemento;
import com.proyecto.patrones.repositorio.RepositorioPedidoMemoria;
import com.proyecto.patrones.repositorio.RepositorioProductoMemoria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ServicioComando {

    private final RepositorioPedidoMemoria repoPedido;
    private final RepositorioProductoMemoria repoProducto;
    private final ServicioProducto servicioProducto;
    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, Deque<PedidoMemento>> mementos = new ConcurrentHashMap<>();
    // Usamos Map para estructura flexible sin crear DTO extra
    private final List<Map<String, String>> historial = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger contador = new AtomicInteger(0);

    public ServicioComando(RepositorioPedidoMemoria repoPedido, RepositorioProductoMemoria repoProducto, ServicioProducto servicioProducto){
        this.repoPedido = repoPedido;
        this.repoProducto = repoProducto;
        this.servicioProducto = servicioProducto;
    }

    public Pedido crearPedido(PedidoCreateDto dto) {
        String id = "PED-" + String.format("%04d", contador.incrementAndGet());
        Pedido p = new Pedido(id);

        if(dto != null && dto.items != null) {
            dto.items.forEach(i -> p.getItems().add(new PedidoItem(i.getProductoId(), i.getCantidad())));
        }

        Map<String,Integer> snapshot = new HashMap<>();
        double total = 0.0;

        // 1. Validar Stock y Calcular Precios (Con Estrategia)
        for(PedidoItem it : p.getItems()){
            var prod = repoProducto.obtenerPorId(it.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no existe: " + it.getProductoId()));

            if(prod.getStock() < it.getCantidad())
                throw new RuntimeException("Stock insuficiente: " + prod.getNombre());

            snapshot.put(prod.getId(), prod.getStock());

            // Usar precio real de la estrategia
            ProductoDto info = servicioProducto.obtenerProducto(prod.getId());
            total += info.precioActual.doubleValue() * it.getCantidad();
        }

        // 2. Memento
        try {
            pushMemento(id, new PedidoMemento(snapshot, mapper.writeValueAsString(p)));
        } catch(Exception e){ e.printStackTrace(); }

        // 3. Ejecutar (Bajar Stock)
        for(PedidoItem it : p.getItems()){
            var prod = repoProducto.obtenerPorId(it.getProductoId()).get();
            prod.setStock(prod.getStock() - it.getCantidad());
            prod.incrementarVentas(it.getCantidad());
            repoProducto.guardar(prod);
        }

        p.setTotal(total);
        p.setEstado("CREADO");
        repoPedido.guardar(p);

        registrarEvento(id, "CREADO", "Pedido creado por S/ " + total);
        return p;
    }

    public boolean deshacer(String pedidoId){
        Deque<PedidoMemento> pila = mementos.get(pedidoId);
        if(pila == null || pila.isEmpty()) return false;

        PedidoMemento mem = pila.pop();

        // Restaurar Stocks
        mem.getStockSnapshot().forEach((pid, stock) -> {
            repoProducto.obtenerPorId(pid).ifPresent(p -> {
                p.setStock(stock);
                // Opcional: decrementar ventas si se desea revertir impacto en estrategia
                repoProducto.guardar(p);
            });
        });

        // Actualizar estado pedido
        Pedido p = repoPedido.obtener(pedidoId);
        if(p != null) {
            p.setEstado("CANCELADO");
            repoPedido.guardar(p);
        }

        registrarEvento(pedidoId, "CANCELADO", "Pedido revertido (Undo)");
        return true;
    }

    // Método compatibilidad
    public Pedido crearPedidoEjemplo(){
        return new Pedido("demo");
    }

    private void pushMemento(String id, PedidoMemento m){
        mementos.computeIfAbsent(id, k->new ArrayDeque<>()).push(m);
    }

    private void registrarEvento(String id, String estado, String accion){
        Map<String, String> evento = new HashMap<>();
        evento.put("id", id);
        evento.put("estado", estado);
        evento.put("accion", accion);
        evento.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        historial.add(0, evento);
    }

    public List<Map<String, String>> obtenerHistorial(){ return new ArrayList<>(historial); }
}