package com.proyecto.patrones.controlador;

import com.proyecto.patrones.dominio.Producto;
import com.proyecto.patrones.patrones.observer.ServicioNotificacion;
import com.proyecto.patrones.repositorio.RepositorioProductoMemoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventario")
public class InventarioControlador {

    private final RepositorioProductoMemoria repo;
    private final ServicioNotificacion notif;

    public InventarioControlador(RepositorioProductoMemoria repo, ServicioNotificacion notif){
        this.repo = repo;
        this.notif = notif;
    }

    @GetMapping("/productos/all")
    public List<Producto> listarTodos(){
        return repo.obtenerTodos();
    }

    @PutMapping("/stock")
    public ResponseEntity<?> setStock(@RequestBody Map<String,Object> body){
        try {
            String productoId = (String) body.get("productoId");
            // Usamos un método seguro para convertir a int, venga como String o Number
            int stock = safeInt(body.get("stock"));

            var op = repo.obtenerPorId(productoId);
            if(op.isEmpty()) return ResponseEntity.status(404).body(Map.of("error","no_encontrado"));
            Producto p = op.get();

            p.setStock(stock);
            repo.guardar(p); // Persistir cambio en memoria

            // Observer Check
            if(p.getStock() < p.getStockMinimo()){
                notif.generar(p.getId(), "STOCK BAJO ("+p.getStock()+")", List.of("GERENTE","COMPRAS"));
            }

            return ResponseEntity.ok(Map.of("ok",true, "nuevoStock", stock));
        } catch(Exception ex){
            ex.printStackTrace(); // Ver error en consola del servidor
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/minimo")
    public ResponseEntity<?> setMinimo(@RequestBody Map<String,Object> body){
        try {
            String productoId = (String) body.get("productoId");
            int min = safeInt(body.get("minimo"));

            var op = repo.obtenerPorId(productoId);
            if(op.isEmpty()) return ResponseEntity.status(404).body(Map.of("error","no_encontrado"));
            Producto p = op.get();

            p.setStockMinimo(min);
            repo.guardar(p);
            return ResponseEntity.ok(Map.of("ok",true));
        } catch(Exception ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    // Método auxiliar para evitar ClassCastException
    private int safeInt(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else if (obj instanceof String) {
            return Integer.parseInt((String) obj);
        }
        throw new IllegalArgumentException("Valor numérico inválido: " + obj);
    }
}