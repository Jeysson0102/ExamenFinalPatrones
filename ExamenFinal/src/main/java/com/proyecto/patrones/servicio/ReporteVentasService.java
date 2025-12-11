package com.proyecto.patrones.servicio;

import com.proyecto.patrones.repositorio.RepositorioPedidoMemoria;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteVentasService {
    private final RepositorioPedidoMemoria repo;

    public ReporteVentasService(RepositorioPedidoMemoria repo){ this.repo = repo; }

    public String generarResumen(){
        var pedidos = repo.obtenerTodos().values();

        long totalPedidos = pedidos.size();

        // Calcular ingresos totales sumando el total de todos los pedidos válidos (no cancelados)
        double ingresosTotales = pedidos.stream()
                .filter(p -> !"CANCELADO".equals(p.getEstado())) // Filtramos los cancelados
                .mapToDouble(p -> p.getTotal())
                .sum();

        // Agrupar por estado para el reporte
        Map<String, Long> porEstado = pedidos.stream()
                .collect(Collectors.groupingBy(p -> p.getEstado(), Collectors.counting()));

        // Formato solicitado: Reporte de Pedidos
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE FINANCIERO DE PEDIDOS ===\n");
        sb.append(String.format("- Total Ingresos: S/ %.2f\n", ingresosTotales));
        sb.append(String.format("- Total Pedidos: %d\n", totalPedidos));
        sb.append("- Desglose por Estado:\n");
        porEstado.forEach((k,v) -> sb.append(String.format("  * %s: %d\n", k, v)));

        return sb.toString();
    }
}