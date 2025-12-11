package com.proyecto.patrones.dto;
public class ResultadoPagoDto {
    public boolean exito;
    public String transaccionId;
    public String mensaje;
    public ResultadoPagoDto() {}
    public ResultadoPagoDto(boolean exito, String tx, String msg){ this.exito = exito; this.transaccionId = tx; this.mensaje = msg; }
}
