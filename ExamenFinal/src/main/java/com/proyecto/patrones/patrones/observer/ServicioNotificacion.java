package com.proyecto.patrones.patrones.observer;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Service
public class ServicioNotificacion {
    private final List<NotificacionRegistro> registros = new CopyOnWriteArrayList<>();

    public void generar(String productoId, String mensaje, List<String> roles){
        registros.add(new NotificacionRegistro(productoId, mensaje, roles));
    }

    public List<NotificacionRegistro> obtenerPorRol(String rol){
        if(rol == null || rol.isBlank()) return List.copyOf(registros);
        String r = rol.trim().toUpperCase();
        return registros.stream()
                .filter(n -> n.roles.stream().map(String::toUpperCase).anyMatch(rr -> rr.equals(r)))
                .collect(Collectors.toList());
    }

    public List<NotificacionRegistro> obtenerTodas(){ return List.copyOf(registros); }
}
