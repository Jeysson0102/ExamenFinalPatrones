package com.proyecto.patrones.patrones.adapter;

import com.proyecto.patrones.dto.ResultadoPagoDto;
import com.proyecto.patrones.dto.SolicitudPago;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component("plin")
public class PlinAdaptador implements InterfazPasarela {
    @Override public ResultadoPagoDto autorizar(SolicitudPago req) {
        return new ResultadoPagoDto(true, "PLIN-" + UUID.randomUUID(), "autorizado_plin");
    }
    @Override public String id(){ return "plin"; }
}
