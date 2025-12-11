package com.proyecto.patrones.dto;
import java.math.BigDecimal;

public class ProductoDto {
    public String id;
    public String nombre;
    public BigDecimal precioBase;
    public BigDecimal precioActual;
    public int stock;
    public int stockMinimo;

    // Datos para la UI de edición
    public String estrategiaTipo;
    public BigDecimal estrategiaValor;
}