package com.proyecto.patrones.patrones.memento;

import java.util.Map;

public class PedidoMemento {
    private final Map<String,Integer> stockSnapshot;
    private final String pedidoJson;

    public PedidoMemento(Map<String,Integer> stockSnapshot, String pedidoJson){
        this.stockSnapshot = stockSnapshot;
        this.pedidoJson = pedidoJson;
    }

    public Map<String,Integer> getStockSnapshot(){ return stockSnapshot; }
    public String getPedidoJson(){ return pedidoJson; }
}
