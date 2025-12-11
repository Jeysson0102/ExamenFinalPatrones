package com.proyecto.patrones.dto;

import com.proyecto.patrones.dominio.PedidoItem;
import java.util.List;

public class PedidoCreateDto {
    public List<PedidoItem> items;
    public String paymentMethod; // opcional: "paypal", "yape", "plin"
}
