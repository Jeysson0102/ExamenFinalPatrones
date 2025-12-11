package com.proyecto.patrones.controlador;

import com.proyecto.patrones.servicio.ServicioPago;
import com.proyecto.patrones.servicio.ServicioProducto;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminControlador {
    private final ServicioPago servicioPago;
    private final ServicioProducto servicioProducto;

    public AdminControlador(ServicioPago servicioPago, ServicioProducto servicioProducto){
        this.servicioPago = servicioPago;
        this.servicioProducto = servicioProducto;
    }

    // Endpoint para editar estrategia POR PRODUCTO
    @PostMapping("/producto/{id}/estrategia")
    public Map<String, Object> setEstrategiaProducto(@PathVariable String id,
                                                     @RequestParam String tipo,
                                                     @RequestParam(required=false, defaultValue="0") BigDecimal valor) {
        servicioProducto.actualizarEstrategia(id, tipo, valor);
        return Map.of("ok", true, "id", id, "estrategia", tipo);
    }

    @PostMapping("/pasarela/{id}/habilitar")
    public Map<String,Object> habilitar(@PathVariable String id, @RequestParam boolean habilitar){
        servicioPago.setEstado(id, habilitar);
        return Map.of("ok", true);
    }

    @GetMapping("/pasarelas/estado")
    public Map<String,Boolean> estado(){ return servicioPago.estadoPasarelas(); }
}