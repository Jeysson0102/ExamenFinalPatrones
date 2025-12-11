package com.proyecto.patrones.patrones.adapter;

import com.proyecto.patrones.dto.ResultadoPagoDto;
import com.proyecto.patrones.dto.SolicitudPago;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component("yape")
public class YapeAdaptador implements InterfazPasarela {
    @Override public ResultadoPagoDto autorizar(SolicitudPago req) {
        return new ResultadoPagoDto(true, "YAPE-" + UUID.randomUUID(), "autorizado_yape");
    }
    @Override public String id(){ return "yape"; }
}
