package com.proyecto.patrones.dominio;

public class PedidoItem {
    private String productoId;
    private int cantidad;
    public PedidoItem() {}
    public PedidoItem(String productoId, int cantidad){ this.productoId = productoId; this.cantidad = cantidad; }
    public String getProductoId(){ return productoId; }
    public int getCantidad(){ return cantidad; }
}
