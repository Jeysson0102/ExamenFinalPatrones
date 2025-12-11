package com.proyecto.patrones.controlador;

import com.proyecto.patrones.patrones.observer.NotificacionRegistro;
import com.proyecto.patrones.patrones.observer.ServicioNotificacion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionControlador {
    private final ServicioNotificacion servicio;

    public NotificacionControlador(ServicioNotificacion servicio){ this.servicio = servicio; }

    // GET /notificaciones?rol=GERENTE
    @GetMapping
    public List<NotificacionRegistro> obtener(@RequestParam(name="rol", required=false) String rol){
        return servicio.obtenerPorRol(rol);
    }
}
