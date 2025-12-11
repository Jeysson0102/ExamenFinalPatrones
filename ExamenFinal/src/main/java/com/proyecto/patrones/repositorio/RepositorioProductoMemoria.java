package com.proyecto.patrones.repositorio;

import com.proyecto.patrones.dominio.Producto;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositorioProductoMemoria {
    private final Map<String, Producto> datos = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        // 20 productos tecnológicos reales (precios en SOLES)
        guardar(new Producto("p1", "Laptop Gamer RTX 4060 16GB", BigDecimal.valueOf(4299), 20, 5));
        guardar(new Producto("p2", "Smartphone Ultra 256GB", BigDecimal.valueOf(2899), 30, 6));
        guardar(new Producto("p3", "Monitor 27\" 144Hz IPS", BigDecimal.valueOf(899), 18, 4));
        guardar(new Producto("p4", "Auriculares Noise Cancelling", BigDecimal.valueOf(349), 40, 5));
        guardar(new Producto("p5", "Teclado Mecánico RGB", BigDecimal.valueOf(199), 50, 8));
        guardar(new Producto("p6", "Mouse Inalámbrico Profesional", BigDecimal.valueOf(149), 45, 6));
        guardar(new Producto("p7", "SSD NVMe 1TB", BigDecimal.valueOf(379), 28, 5));
        guardar(new Producto("p8", "Placa Madre Z690", BigDecimal.valueOf(1299), 10, 3));
        guardar(new Producto("p9", "Fuente 750W Gold", BigDecimal.valueOf(399), 25, 5));
        guardar(new Producto("p10", "Gabinete ATX Premium", BigDecimal.valueOf(299), 15, 3));
        guardar(new Producto("p11", "Cámara Web 4K", BigDecimal.valueOf(249), 35, 6));
        guardar(new Producto("p12", "Micrófono USB Estudio", BigDecimal.valueOf(279), 22, 4));
        guardar(new Producto("p13", "Router Wifi 6", BigDecimal.valueOf(349), 30, 6));
        guardar(new Producto("p14", "Smartwatch Premium", BigDecimal.valueOf(1099), 18, 3));
        guardar(new Producto("p15", "Tablet Pro 11\" 256GB", BigDecimal.valueOf(2199), 12, 2));
        guardar(new Producto("p16", "Cargador GaN 100W", BigDecimal.valueOf(129), 70, 10));
        guardar(new Producto("p17", "Soporte Monitor Dual", BigDecimal.valueOf(189), 40, 6));
        guardar(new Producto("p18", "Adaptador USB-C 7 en 1", BigDecimal.valueOf(119), 55, 8));
        guardar(new Producto("p19", "SSD SATA 2TB", BigDecimal.valueOf(599), 20, 4));
        guardar(new Producto("p20", "Disipador RGB Premium", BigDecimal.valueOf(239), 16, 3));
    }

    public void guardar(Producto p){ datos.put(p.getId(), p); }
    public Optional<Producto> obtenerPorId(String id){ return Optional.ofNullable(datos.get(id)); }
    public List<Producto> obtenerTodos(){ return new ArrayList<>(datos.values()); }

    public List<Producto> buscar(String texto){
        if(texto==null || texto.isBlank()) return obtenerTodos();
        String t = texto.toLowerCase();
        List<Producto> out = new ArrayList<>();
        for(Producto p: datos.values()){
            if(p.getNombre().toLowerCase().contains(t) || p.getId().toLowerCase().contains(t)) out.add(p);
        }
        return out;
    }

    public void eliminar(String id){ datos.remove(id); }
}
