package com.proyecto.patrones.patrones.strategy;

import com.proyecto.patrones.dominio.Producto;
import java.math.BigDecimal;

public interface EstrategiaPrecio {
    BigDecimal calcular(Producto p);
}