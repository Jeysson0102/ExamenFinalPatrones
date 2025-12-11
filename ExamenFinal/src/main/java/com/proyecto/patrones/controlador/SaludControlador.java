package com.proyecto.patrones.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class SaludControlador {
    @GetMapping("/salud")
    public Map<String, String> salud() {
        return Map.of("estado", "UP", "app", "examen-final");
    }
}