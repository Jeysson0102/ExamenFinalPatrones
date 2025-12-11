package com.proyecto.patrones.patrones.strategy;

import com.proyecto.patrones.dominio.Producto;
import java.math.BigDecimal;

public class EstandardEstrategiaPrecio implements EstrategiaPrecio {
    @Override
    public BigDecimal calcular(Producto p) {
        return p.getPrecioBase();
    }
}