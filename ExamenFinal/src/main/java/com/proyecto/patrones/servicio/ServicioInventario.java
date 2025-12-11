package com.proyecto.patrones.servicio;

import com.proyecto.patrones.dominio.Producto;
import com.proyecto.patrones.patrones.observer.NotificacionRegistro;
import com.proyecto.patrones.patrones.observer.ServicioNotificacion;
import com.proyecto.patrones.repositorio.RepositorioProductoMemoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioInventario {
    private final RepositorioProductoMemoria repo;
    private final ServicioNotificacion servicioNotificacion; // Concrete Observer wrapper

    public ServicioInventario(RepositorioProductoMemoria repo, ServicioNotificacion servicioNotificacion){
        this.repo = repo;
        this.servicioNotificacion = servicioNotificacion;
    }

    public Producto actualizarStock(String productoId, int nuevoStock){
        Producto p = repo.obtenerPorId(productoId).orElseThrow(()-> new RuntimeException("producto_no_encontrado"));
        p.setStock(nuevoStock);
        repo.guardar(p);
        verificarStockBajo(p);
        return p;
    }

    public Producto actualizarMinimo(String productoId, int nuevoMinimo){
        Producto p = repo.obtenerPorId(productoId).orElseThrow(()-> new RuntimeException("producto_no_encontrado"));
        p.setStockMinimo(nuevoMinimo);
        repo.guardar(p);
        verificarStockBajo(p);
        return p;
    }

    private void verificarStockBajo(Producto p){
        if(p.getStock() < p.getStockMinimo()){
            // Notificar (Observer logic)
            List<String> roles = List.of("GERENTE", "COMPRAS");
            servicioNotificacion.generar(p.getId(),
                    "ALERTA: Stock " + p.getStock() + " inferior al mínimo " + p.getStockMinimo(),
                    roles);
        }
    }

    public Producto obtener(String id) {
        return repo.obtenerPorId(id).orElseThrow();
    }
}