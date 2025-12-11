package com.proyecto.patrones.patrones.iterator;

import com.proyecto.patrones.dominio.Producto;

import java.util.Iterator;
import java.util.List;

public class IteradorProducto implements Iterator<Producto> {
    private final List<Producto> lista;
    private int cursor = 0;
    public IteradorProducto(List<Producto> lista){ this.lista = lista; }
    @Override public boolean hasNext(){ return cursor < lista.size(); }
    @Override public Producto next(){ return lista.get(cursor++); }
}
