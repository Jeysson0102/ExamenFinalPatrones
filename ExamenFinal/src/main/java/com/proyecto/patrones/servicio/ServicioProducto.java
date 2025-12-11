package com.proyecto.patrones.servicio;

import com.proyecto.patrones.dominio.Producto;
import com.proyecto.patrones.dto.ProductoDto;
import com.proyecto.patrones.patrones.strategy.*;
import com.proyecto.patrones.repositorio.RepositorioProductoMemoria;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioProducto {
    private final RepositorioProductoMemoria repo;

    public ServicioProducto(RepositorioProductoMemoria repo){ this.repo = repo; }

    public List<ProductoDto> obtenerProductos(int pagina, int tamano, String filtro){
        List<Producto> all = filtro == null || filtro.isBlank() ? repo.obtenerTodos() : repo.buscar(filtro);
        all.sort((a,b)-> a.getNombre().compareToIgnoreCase(b.getNombre()));

        int desde = Math.min(pagina * tamano, all.size());
        int hasta = Math.min(desde + tamano, all.size());

        return all.subList(desde, hasta).stream()
                .map(this::mapToDto) // Mapeo inteligente
                .collect(Collectors.toList());
    }

    public ProductoDto obtenerProducto(String id){
        return repo.obtenerPorId(id).map(this::mapToDto).orElse(null);
    }

    // FACTORY METHOD para Strategy
    private ProductoDto mapToDto(Producto p) {
        ProductoDto d = new ProductoDto();
        d.id = p.getId(); d.nombre = p.getNombre(); d.precioBase = p.getPrecioBase();
        d.stock = p.getStock(); d.stockMinimo = p.getStockMinimo();
        d.estrategiaTipo = p.getEstrategiaTipo();
        d.estrategiaValor = p.getEstrategiaValor();

        EstrategiaPrecio strategy;
        switch (p.getEstrategiaTipo().toLowerCase()) {
            case "descuento":
                // Usa el valor del producto como porcentaje
                BigDecimal pct = p.getEstrategiaValor() != null ? p.getEstrategiaValor() : BigDecimal.ZERO;
                strategy = new DescuentoPorcentajeEstrategia(pct);
                break;
            case "dinamico":
                // Dinámico puro o con ajuste manual
                strategy = new PrecioDinamicoEstrategia();
                break;
            default:
                strategy = new EstandardEstrategiaPrecio();
        }

        try { d.precioActual = strategy.calcular(p); }
        catch(Exception e) { d.precioActual = p.getPrecioBase(); }
        return d;
    }

    // Método para actualizar la estrategia desde el AdminControlador
    public void actualizarEstrategia(String id, String tipo, BigDecimal valor){
        repo.obtenerPorId(id).ifPresent(p -> {
            p.setEstrategia(tipo, valor);
            repo.guardar(p);
        });
    }
}