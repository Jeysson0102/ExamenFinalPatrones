package com.proyecto.patrones.servicio;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ServicioPago {
    private final Map<String,Boolean> estado = new ConcurrentHashMap<>();

    public ServicioPago(){
        estado.put("paypal", true);
        estado.put("yape", true);
        estado.put("plin", true);
    }

    public Map<String,Boolean> estadoPasarelas(){ return estado; }

    public void setEstado(String id, boolean v){ estado.put(id.toLowerCase(), v); }
}
