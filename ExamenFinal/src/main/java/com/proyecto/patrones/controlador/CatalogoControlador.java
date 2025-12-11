package com.proyecto.patrones.controlador;

import com.proyecto.patrones.dto.ProductoDto;
import com.proyecto.patrones.servicio.ServicioProducto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class CatalogoControlador {
    private final ServicioProducto servicioProducto;
    public CatalogoControlador(ServicioProducto servicioProducto){ this.servicioProducto = servicioProducto; }

    @GetMapping
    public List<ProductoDto> listar(@RequestParam(defaultValue="0") int pagina,
                                    @RequestParam(defaultValue="12") int tamano,
                                    @RequestParam(required=false) String filtro){
        return servicioProducto.obtenerProductos(pagina, tamano, filtro);
    }

    @GetMapping("/{id}")
    public ProductoDto obtener(@PathVariable String id){ return servicioProducto.obtenerProducto(id); }
}
