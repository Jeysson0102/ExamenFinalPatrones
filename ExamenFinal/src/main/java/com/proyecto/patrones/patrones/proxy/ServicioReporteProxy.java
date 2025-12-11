package com.proyecto.patrones.patrones.proxy;

import com.proyecto.patrones.dominio.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ServicioReporteProxy implements ServicioReporte {
    private final ServicioReporteImpl real;
    public ServicioReporteProxy(ServicioReporteImpl real){ this.real = real; }

    @Override
    public String generarReporteFinanciero(Usuario usuario) {
        if (!usuario.tieneRol("GERENTE") && !usuario.tieneRol("CONTADOR")) {
            throw new RuntimeException("ACCESO_DENEGADO");
        }
        return real.generarReporteFinanciero(usuario);
    }
}
