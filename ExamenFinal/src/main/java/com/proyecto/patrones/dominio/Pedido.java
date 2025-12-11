package com.proyecto.patrones.dominio;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private String estado; // NUEVO, PROCESADO, PAGADO, CANCELADO
    private List<PedidoItem> items = new ArrayList<>();
    private double total;

    public Pedido() {}
    public Pedido(String id){ this.id = id; this.estado = "NUEVO"; this.total = 0.0; }

    public String getId(){ return id; }
    public String getEstado(){ return estado; }
    public void setEstado(String estado){ this.estado = estado; }
    public List<PedidoItem> getItems(){ return items; }
    public double getTotal(){ return total; }
    public void setTotal(double total){ this.total = total; }
}
