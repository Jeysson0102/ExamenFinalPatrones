package com.proyecto.patrones.patrones.observer;

import com.proyecto.patrones.dominio.Producto;

public interface ObservadorStock {
    void alDetectarStockBajo(Producto p);
}
