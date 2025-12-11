package com.proyecto.patrones.patrones.adapter;

import com.proyecto.patrones.dto.ResultadoPagoDto;
import com.proyecto.patrones.dto.SolicitudPago;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component("paypal")
public class PaypalAdaptador implements InterfazPasarela {
    @Override public ResultadoPagoDto autorizar(SolicitudPago req) {
        return new ResultadoPagoDto(true, "PAY-" + UUID.randomUUID(), "autorizado_paypal");
    }
    @Override public String id(){ return "paypal"; }
}
