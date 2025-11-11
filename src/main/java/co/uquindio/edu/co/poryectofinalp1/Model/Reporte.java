package co.uquindio.edu.co.poryectofinalp1.Model;

import co.uquindio.edu.co.poryectofinalp1.Repositorio.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * GeneradorReportes - Servicio para generar reportes del sistema
 * Cumple con el requisito de "Generación de reportes" del proyecto
 */
public class Reporte {

    private static Reporte instancia;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private Reporte() {}

    public static Reporte getInstancia() {
        if (instancia == null) {
            instancia = new Reporte();
        }
        return instancia;
    }

    /**
     * Genera reporte de todas las transacciones del sistema
     */
    public String generarReporteTransacciones() {
        ListTransaccion listaTransacciones = ListTransaccion.getInstancia();
        List<Transaccion> transacciones = listaTransacciones.getTransacciones();

        if (transacciones.isEmpty()) {
            return "No hay transacciones registradas en el sistema.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║       REPORTE GENERAL DE TRANSACCIONES                ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Fecha de generación: %s\n",
                LocalDateTime.now().format(formatter)));
        reporte.append(String.format("Total de transacciones: %d\n\n", transacciones.size()));

        // Estadísticas por tipo
        Map<String, Long> porTipo = transacciones.stream()
                .collect(Collectors.groupingBy(Transaccion::getTipo, Collectors.counting()));

        reporte.append("ESTADÍSTICAS POR TIPO:\n");
        reporte.append("─────────────────────────────────\n");
        porTipo.forEach((tipo, cantidad) ->
                reporte.append(String.format("  %s: %d transacciones\n", tipo, cantidad)));

        // Monto total transaccionado
        double montoTotal = transacciones.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        reporte.append(String.format("\nMonto total transaccionado: $%.2f\n\n", montoTotal));

        // Listado de transacciones
        reporte.append("DETALLE DE TRANSACCIONES:\n");
        reporte.append("═════════════════════════════════════════════════════════\n");
        for (int i = 0; i < transacciones.size(); i++) {
            reporte.append(String.format("%d. %s\n", i + 1, transacciones.get(i).toString()));
        }

        return reporte.toString();
    }

    /**
     * Genera reporte de transacciones por cliente específico
     */
    public String generarReporteTransaccionesPorCliente(String numeroCuenta) {
        ListTransaccion listaTransacciones = ListTransaccion.getInstancia();
        ListCliente listaClientes = ListCliente.getInstance();

        Cliente cliente = listaClientes.buscarClientePorNumeroCuenta(numeroCuenta);
        if (cliente == null) {
            return "No se encontró el cliente con número de cuenta: " + numeroCuenta;
        }

        List<Transaccion> transacciones = listaTransacciones.buscarTransaccionesPorCuenta(numeroCuenta);

        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║       REPORTE DE TRANSACCIONES POR CLIENTE            ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Cliente: %s\n", cliente.getNombre()));
        reporte.append(String.format("Cédula: %s\n", cliente.getCedula()));
        reporte.append(String.format("Número de cuenta: %s\n", numeroCuenta));
        reporte.append(String.format("Fecha de generación: %s\n\n",
                LocalDateTime.now().format(formatter)));

        if (transacciones.isEmpty()) {
            reporte.append("Este cliente no tiene transacciones registradas.\n");
            return reporte.toString();
        }

        reporte.append(String.format("Total de transacciones: %d\n\n", transacciones.size()));

        // Estadísticas
        Map<String, Long> porTipo = transacciones.stream()
                .collect(Collectors.groupingBy(Transaccion::getTipo, Collectors.counting()));

        reporte.append("ESTADÍSTICAS:\n");
        reporte.append("─────────────────────────────────\n");
        porTipo.forEach((tipo, cantidad) ->
                reporte.append(String.format("  %s: %d operaciones\n", tipo, cantidad)));

        double montoTotal = transacciones.stream()
                .mapToDouble(Transaccion::getMonto)
                .sum();
        reporte.append(String.format("\nMonto total: $%.2f\n\n", montoTotal));

        // Listado
        reporte.append("DETALLE:\n");
        reporte.append("═════════════════════════════════════════════════════════\n");
        for (int i = 0; i < transacciones.size(); i++) {
            reporte.append(String.format("%d. %s\n", i + 1, transacciones.get(i).toString()));
        }

        return reporte.toString();
    }

    /**
     * Genera reporte de empleados del sistema
     */
    public String generarReporteEmpleados() {
        ListEmpleado listaEmpleados = ListEmpleado.getInstancia();
        List<Empleado> empleados = listaEmpleados.getEmpleados();

        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║           REPORTE DE EMPLEADOS                        ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Fecha: %s\n", LocalDateTime.now().format(formatter)));
        reporte.append(String.format("Total de empleados: %d\n\n", empleados.size()));

        if (empleados.isEmpty()) {
            reporte.append("No hay empleados registrados.\n");
            return reporte.toString();
        }

        double nominaTotal = empleados.stream()
                .mapToDouble(Empleado::getSalario)
                .sum();
        reporte.append(String.format("Nómina total: $%.2f\n\n", nominaTotal));

        reporte.append("LISTADO DE EMPLEADOS:\n");
        reporte.append("═════════════════════════════════════════════════════════\n");
        for (int i = 0; i < empleados.size(); i++) {
            Empleado e = empleados.get(i);
            reporte.append(String.format("%d. %s\n", i + 1, e.toString()));
        }

        return reporte.toString();
    }

    /**
     * Genera reporte de clientes del sistema
     */
    public String generarReporteClientes() {
        ListCliente listaClientes = ListCliente.getInstance();
        List<Cliente> clientes = listaClientes.getClientes();

        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║           REPORTE DE CLIENTES                         ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Fecha: %s\n", LocalDateTime.now().format(formatter)));
        reporte.append(String.format("Total de clientes: %d\n\n", clientes.size()));

        if (clientes.isEmpty()) {
            reporte.append("No hay clientes registrados.\n");
            return reporte.toString();
        }

        reporte.append("LISTADO DE CLIENTES:\n");
        reporte.append("═════════════════════════════════════════════════════════\n");
        for (int i = 0; i < clientes.size(); i++) {
            Cliente c = clientes.get(i);
            reporte.append(String.format("%d. %s\n", i + 1, c.toString()));
        }

        return reporte.toString();
    }

    /**
     * Detecta operaciones sospechosas
     * (Múltiples transacciones grandes en poco tiempo)
     */
    public String generarReporteOperacionesSospechosas(double montoSospechoso) {
        ListTransaccion listaTransacciones = ListTransaccion.getInstancia();
        List<Transaccion> transacciones = listaTransacciones.getTransacciones();

        List<Transaccion> sospechosas = transacciones.stream()
                .filter(t -> t.getMonto() >= montoSospechoso)
                .collect(Collectors.toList());

        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║       REPORTE DE OPERACIONES SOSPECHOSAS              ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Criterio: Transacciones ≥ $%.2f\n", montoSospechoso));
        reporte.append(String.format("Fecha: %s\n\n", LocalDateTime.now().format(formatter)));

        if (sospechosas.isEmpty()) {
            reporte.append("No se detectaron operaciones sospechosas.\n");
            return reporte.toString();
        }

        reporte.append(String.format("ALERTA: Se detectaron %d operaciones sospechosas\n\n",
                sospechosas.size()));

        reporte.append("DETALLE:\n");
        reporte.append("═════════════════════════════════════════════════════════\n");
        for (int i = 0; i < sospechosas.size(); i++) {
            reporte.append(String.format("⚠️  %d. %s\n", i + 1, sospechosas.get(i).toString()));
        }

        return reporte.toString();
    }

    /**
     * Genera un reporte consolidado del banco
     */
    public String generarReporteConsolidado() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("╔════════════════════════════════════════════════════════╗\n");
        reporte.append("║       REPORTE CONSOLIDADO BANCO UQ                    ║\n");
        reporte.append("╚════════════════════════════════════════════════════════╝\n\n");
        reporte.append(String.format("Fecha: %s\n\n", LocalDateTime.now().format(formatter)));

        // Clientes
        int totalClientes = ListCliente.getInstance().getClientes().size();
        reporte.append(String.format("📊 Total de clientes: %d\n", totalClientes));

        // Empleados
        int totalEmpleados = ListEmpleado.getInstancia().getEmpleados().size();
        reporte.append(String.format("👥 Total de empleados: %d\n", totalEmpleados));

        // Transacciones
        int totalTransacciones = ListTransaccion.getInstancia().getTransacciones().size();
        reporte.append(String.format("💳 Total de transacciones: %d\n", totalTransacciones));

        // Usuarios
        int totalUsuarios = ListUsuario.getInstancia().getUsuarios().size();
        reporte.append(String.format("🔐 Total de usuarios: %d\n\n", totalUsuarios));

        return reporte.toString();
    }
}