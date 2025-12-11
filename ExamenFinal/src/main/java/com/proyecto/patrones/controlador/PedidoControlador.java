package com.proyecto.patrones.controlador;

import com.proyecto.patrones.dominio.Pedido;
import com.proyecto.patrones.servicio.ServicioComando;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoControlador {
    private final ServicioComando servicioComando;
    public PedidoControlador(ServicioComando servicioComando){ this.servicioComando = servicioComando; }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido() {
        return ResponseEntity.ok(servicioComando.crearPedidoEjemplo());
    }

    @PostMapping("/{id}/deshacer")
    public ResponseEntity<String> deshacer(@PathVariable String id) {
        boolean ok = servicioComando.deshacer(id);
        return ResponseEntity.ok(ok ? "restaurado" : "sin_memento");
    }
}