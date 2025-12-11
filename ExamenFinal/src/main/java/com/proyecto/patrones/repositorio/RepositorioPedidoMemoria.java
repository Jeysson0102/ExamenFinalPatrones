package com.proyecto.patrones.repositorio;

import com.proyecto.patrones.dominio.Pedido;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RepositorioPedidoMemoria {
    private final Map<String, Pedido> datos = new ConcurrentHashMap<>();

    public void guardar(Pedido p){ datos.put(p.getId(), p); }
    public Pedido obtener(String id){ return datos.get(id); }
    public void eliminar(String id){ datos.remove(id); }
    public Map<String, Pedido> obtenerTodos(){ return datos; }
}
