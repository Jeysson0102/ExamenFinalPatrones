package com.proyecto.patrones.dominio;

import java.math.BigDecimal;

public class Producto {
    private String id;
    private String nombre;
    private BigDecimal precioBase;
    private int stock;
    private int stockMinimo;
    private int ventas; // Para estrategia dinámica

    // CONFIGURACIÓN STRATEGY (Persistencia por producto)
    private String estrategiaTipo = "estandar"; // estandar, descuento, dinamico
    private BigDecimal estrategiaValor = BigDecimal.ZERO; // % descuento o ajuste

    public Producto() {}
    public Producto(String id, String nombre, BigDecimal precioBase, int stock, int stockMinimo) {
        this.id = id;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.ventas = 0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public int getStock() { return stock; }
    public int getStockMinimo() { return stockMinimo; }
    public int getVentas() { return ventas; }
    public String getEstrategiaTipo() { return estrategiaTipo; }
    public BigDecimal getEstrategiaValor() { return estrategiaValor; }

    public void setStock(int s) { this.stock = s; }
    public void setStockMinimo(int m) { this.stockMinimo = m; }
    public void incrementarVentas(int q){ this.ventas += q; }

    public void setEstrategia(String tipo, BigDecimal valor){
        this.estrategiaTipo = tipo;
        this.estrategiaValor = valor;
    }
}