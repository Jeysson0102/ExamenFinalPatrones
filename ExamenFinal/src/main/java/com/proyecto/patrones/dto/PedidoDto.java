package com.proyecto.patrones.dto;
import com.proyecto.patrones.dominio.PedidoItem;
import java.util.List;

public class PedidoDto {
    public String id;
    public String estado;
    public List<PedidoItem> items;
    public double total;
}
