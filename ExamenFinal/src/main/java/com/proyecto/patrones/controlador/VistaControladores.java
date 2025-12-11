package com.proyecto.patrones.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador de vistas estáticas.
 * IMPORTANTE: usamos el prefijo /ui/* para evitar colisiones con los endpoints REST (p.ej. /productos, /pagos).
 */
@Controller
public class VistaControladores {

    @GetMapping("/")
    public String root() {
        return "redirect:/index.html";
    }

    @GetMapping("/ui/pagos")
    public String pagos() {
        return "redirect:/pagos/index.html";
    }

    @GetMapping("/ui/reportes")
    public String reportes() {
        return "redirect:/reportes/index.html";
    }

    @GetMapping("/ui/inventario")
    public String inventario() {
        return "redirect:/inventario/index.html";
    }

    @GetMapping("/ui/pedidos")
    public String pedidos() {
        return "redirect:/pedidos/index.html";
    }

    @GetMapping("/ui/precios")
    public String precios() {
        return "redirect:/precios/index.html";
    }

    @GetMapping("/ui/productos")
    public String productos() {
        return "redirect:/productos/index.html";
    }
}
