package com.proyecto.patrones.controlador;

import com.proyecto.patrones.patrones.proxy.ServicioReporte;
import com.proyecto.patrones.dominio.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/reportes")
public class ReporteControlador {
    private final ServicioReporte servicioReporte;

    // Almacen de tokens temporales (Simulación de sesión)
    private final Map<String,String> tokenToRole = new ConcurrentHashMap<>();

    // Credenciales Hardcodeadas (REQUERIMIENTO: Proxy con contraseñas específicas)
    private final Map<String,String> credenciales = Map.of(
            "GERENTE", "gerente123",
            "CONTADOR", "contador123",
            "VENDEDOR", "vendedor123"
    );

    public ReporteControlador(ServicioReporte servicioReporte){
        this.servicioReporte = servicioReporte;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String role = body.getOrDefault("role", "").toUpperCase();
        String pass = body.getOrDefault("password", "");

        // Validación de credenciales
        if(credenciales.containsKey(role) && credenciales.get(role).equals(pass)){
            String token = UUID.randomUUID().toString();
            tokenToRole.put(token, role);
            return ResponseEntity.ok(Map.of("token", token, "rol", role));
        }

        return ResponseEntity.status(401).body(Map.of("error", "credenciales_invalidas"));
    }

    @GetMapping("/financieros")
    public ResponseEntity<String> reporte(@RequestHeader(name="Authorization", required=false) String auth){
        if(auth == null || !auth.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body("ERROR: No autenticado (Token faltante)");
        }

        String token = auth.substring("Bearer ".length());
        String role = tokenToRole.get(token);

        if(role == null) {
            return ResponseEntity.status(403).body("ERROR: Token inválido o expirado");
        }

        // Llamada al Proxy (El proxy hará la segunda validación de rol)
        try {
            String reporte = servicioReporte.generarReporteFinanciero(new Usuario("admin", role));
            return ResponseEntity.ok(reporte);
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body("ACCESO DENEGADO POR PROXY: Su rol no tiene permisos financieros.");
        }
    }
}