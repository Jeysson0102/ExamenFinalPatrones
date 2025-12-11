package com.proyecto.patrones.patrones.strategy;

import com.proyecto.patrones.dominio.Producto;
import java.math.BigDecimal;

public class DescuentoPorcentajeEstrategia implements EstrategiaPrecio {
    private final BigDecimal porcentaje;

    // Constructor requerido por ServicioProducto
    public DescuentoPorcentajeEstrategia(BigDecimal porcentaje){
        this.porcentaje = porcentaje != null ? porcentaje : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal calcular(Producto p){
        // Precio Base * (1 - porcentaje)
        return p.getPrecioBase().multiply(BigDecimal.ONE.subtract(porcentaje));
    }
}