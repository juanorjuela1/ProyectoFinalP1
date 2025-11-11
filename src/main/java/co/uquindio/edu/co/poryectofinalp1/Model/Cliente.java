package co.uquindio.edu.co.poryectofinalp1.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente - HERENCIA de Persona + COMPOSICIÓN con CuentaBancaria
 * Representa a una persona que TIENE una o más cuentas bancarias
 * Aplica: HERENCIA + COMPOSICIÓN + POLIMORFISMO + ENCAPSULAMIENTO
 */
public class Cliente extends Persona {
    private List<CuentaBancaria> cuentas;
    private String fechaRegistro;
    private boolean activo;

    public Cliente(String nombre, String cedula, double saldo) {
        super();
        this.cuentas = new ArrayList<>();
        this.activo = true;
    }

    public Cliente(String nombre, String cedula) {
        super(nombre, cedula);
        this.cuentas = new ArrayList<>();
        this.activo = true;
    }

    public Cliente(String nombre, String cedula, String telefono, String email) {
        super(nombre, cedula, telefono, email);
        this.cuentas = new ArrayList<>();
        this.activo = true;
    }

    /**
     * COMPOSICIÓN - Agrega una cuenta bancaria al cliente
     */
    public void agregarCuenta(CuentaBancaria cuenta) {
        if (cuenta != null && !cuentas.contains(cuenta)) {
            cuentas.add(cuenta);
        }
    }

    /**
     * COMPOSICIÓN - Elimina una cuenta bancaria
     */
    public boolean eliminarCuenta(String numeroCuenta) {
        return cuentas.removeIf(c -> c.getNumeroCuenta().equals(numeroCuenta));
    }

    /**
     * COMPOSICIÓN - Busca una cuenta por su número
     */
    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        return cuentas.stream()
                .filter(c -> c.getNumeroCuenta().equals(numeroCuenta))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene la cuenta principal (primera cuenta)
     */
    public CuentaBancaria getCuentaPrincipal() {
        return cuentas.isEmpty() ? null : cuentas.get(0);
    }

    /**
     * Calcula el saldo total de todas las cuentas
     */
    public double getSaldoTotal() {
        return cuentas.stream()
                .mapToDouble(CuentaBancaria::consultarSaldo)
                .sum();
    }

    /**
     * POLIMORFISMO - Implementación específica para Cliente
     */
    @Override
    public String obtenerInformacion() {
        return String.format("Cliente: %s | CC: %s | Cuentas: %d | Saldo Total: $%.2f",
                nombre, cedula, cuentas.size(), getSaldoTotal());
    }

    /**
     * POLIMORFISMO - Implementación específica para Cliente
     */
    @Override
    public String obtenerRol() {
        return "CLIENTE";
    }

    /**
     * Genera un reporte de todas las cuentas del cliente
     */
    public String generarReporteCuentas() {
        if (cuentas.isEmpty()) {
            return "El cliente no tiene cuentas registradas";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CUENTAS ===\n");
        reporte.append(String.format("Cliente: %s\n", nombre));
        reporte.append(String.format("Cédula: %s\n", cedula));
        reporte.append(String.format("Total de Cuentas: %d\n\n", cuentas.size()));

        for (int i = 0; i < cuentas.size(); i++) {
            CuentaBancaria cuenta = cuentas.get(i);
            reporte.append(String.format("Cuenta #%d:\n", i + 1));
            reporte.append(cuenta.generarReporteDetallado());
            reporte.append("\n\n");
        }

        reporte.append(String.format("SALDO TOTAL: $%.2f\n", getSaldoTotal()));
        return reporte.toString();
    }

    // Getters y Setters
    public List<CuentaBancaria> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaBancaria> cuentas) {
        this.cuentas = cuentas;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getNumeroCuenta() {
        return getCuentaPrincipal() != null ? getCuentaPrincipal().getNumeroCuenta() : "SIN CUENTA";
    }

    public void setSaldo(double saldo) {
        if (getCuentaPrincipal() != null) {
            getCuentaPrincipal().setSaldo(saldo);
        }
    }

    public double getSaldo() {
        return getSaldoTotal();
    }

    @Override
    public String toString() {
        return String.format("%s | %s | Cuentas: %d | Saldo: $%.2f",
                nombre, cedula, cuentas.size(), getSaldoTotal());
    }


}