package com.proyecto.patrones.patrones.adapter;

import com.proyecto.patrones.dto.ResultadoPagoDto;
import com.proyecto.patrones.dto.SolicitudPago;

public interface InterfazPasarela {
    ResultadoPagoDto autorizar(SolicitudPago req);
    String id();
}
