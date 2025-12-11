package com.proyecto.patrones.controlador;

import com.proyecto.patrones.dto.PedidoCreateDto;
import com.proyecto.patrones.dominio.Pedido;
import com.proyecto.patrones.servicio.ServicioComando;
import com.proyecto.patrones.servicio.ServicioPago;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/comandos")
public class ComandoControlador {

    private final ServicioComando servicioComando;
    private final ServicioPago servicioPago;

    public ComandoControlador(ServicioComando servicioComando, ServicioPago servicioPago){
        this.servicioComando = servicioComando;
        this.servicioPago = servicioPago;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody PedidoCreateDto body){
        // VALIDACIÓN PASARELA (Singleton)
        if(body.paymentMethod != null){
            String metodo = body.paymentMethod.toLowerCase();
            Boolean habilitado = servicioPago.estadoPasarelas().get(metodo);
            if(habilitado == null || !habilitado){
                return ResponseEntity.status(409) // Conflict
                        .body(Map.of("error", "El método de pago '" + metodo + "' está temporalmente DESHABILITADO."));
            }
        }

        try {
            Pedido p = servicioComando.crearPedido(body);
            return ResponseEntity.status(201).body(p);
        } catch(RuntimeException ex){
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/{id}/deshacer")
    public ResponseEntity<?> deshacer(@PathVariable String id){
        boolean ok = servicioComando.deshacer(id);
        return ResponseEntity.ok(Map.of("ok", ok));
    }

    @GetMapping("/historial")
    public Object historial(){
        return servicioComando.obtenerHistorial();
    }
}