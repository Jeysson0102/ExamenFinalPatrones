package com.proyecto.patrones.patrones.strategy;

import com.proyecto.patrones.dominio.Producto;
import java.math.BigDecimal;

public class PrecioDinamicoEstrategia implements EstrategiaPrecio {

    public PrecioDinamicoEstrategia() {}

    @Override
    public BigDecimal calcular(Producto p) {
        BigDecimal precioBase = p.getPrecioBase();
        BigDecimal factor = BigDecimal.ONE; // 1.0 (100%)

        // 1. Regla de Negocio Automática: Escasez (Stock bajo sube precio)
        if (p.getStock() <= 5 && p.getStock() > 0) {
            factor = factor.add(new BigDecimal("0.10")); // +10% automático por escasez
        }

        // 2. Regla de Negocio Automática: Popularidad (Muchas ventas sube precio)
        if (p.getVentas() > 20) {
            factor = factor.add(new BigDecimal("0.05")); // +5% automático por popularidad
        }

        // 3. Ajuste Manual del Administrador (Permite subir o bajar)
        // El valor viene de p.getEstrategiaValor(). Ej: 0.15 o -0.20
        if (p.getEstrategiaValor() != null) {
            factor = factor.add(p.getEstrategiaValor());
        }

        // Evitar precios negativos
        if (factor.compareTo(BigDecimal.ZERO) < 0) {
            factor = BigDecimal.ZERO;
        }

        return precioBase.multiply(factor);
    }
}