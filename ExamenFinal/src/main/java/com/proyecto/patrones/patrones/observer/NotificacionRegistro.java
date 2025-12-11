package com.proyecto.patrones.patrones.observer;

import java.time.Instant;
import java.util.List;

public class NotificacionRegistro {
    public final String productoId;
    public final String mensaje;
    public final List<String> roles;
    public final Instant timestamp;

    public NotificacionRegistro(String productoId, String mensaje, List<String> roles){
        this.productoId = productoId;
        this.mensaje = mensaje;
        this.roles = roles;
        this.timestamp = Instant.now();
    }
}
