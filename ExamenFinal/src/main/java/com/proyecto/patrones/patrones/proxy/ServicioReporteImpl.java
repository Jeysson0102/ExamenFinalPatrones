package com.proyecto.patrones.patrones.proxy;

import com.proyecto.patrones.dominio.Usuario;
import com.proyecto.patrones.servicio.ReporteVentasService;
import org.springframework.stereotype.Service;

@Service
public class ServicioReporteImpl implements ServicioReporte {
    private final ReporteVentasService reporteVentasService;
    public ServicioReporteImpl(ReporteVentasService reporteVentasService){ this.reporteVentasService = reporteVentasService; }

    @Override
    public String generarReporteFinanciero(Usuario usuario) {
        return reporteVentasService.generarResumen();
    }
}
