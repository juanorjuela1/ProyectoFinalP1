package co.uquindio.edu.co.poryectofinalp1.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase ABSTRACTA CuentaBancaria - Base para todos los tipos de cuenta
 * Implementa ITransaccionable y IReporteable
 * Cumple con ABSTRACCIÓN, INTERFACES y POLIMORFISMO
 */
public abstract class CuentaBancaria implements ITransaccionable, IReporteable {
    protected String numeroCuenta;
    protected double saldo;
    protected String fechaApertura;
    protected boolean activa;
    protected String titularCedula;
    protected String titularNombre;
    protected static int contadorCuentas = 1000;

    public CuentaBancaria() {
        this.activa = true;
        this.fechaApertura = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public CuentaBancaria(String titularNombre, String titularCedula, double saldoInicial) {
        this();
        this.numeroCuenta = generarNumeroCuenta();
        this.titularNombre = titularNombre;
        this.titularCedula = titularCedula;
        this.saldo = saldoInicial;
    }

    /**
     * Genera un número de cuenta único
     */
    protected String generarNumeroCuenta() {
        contadorCuentas++;
        return String.format("%010d", contadorCuentas);
    }

    /**
     * Método abstracto - cada tipo de cuenta tendrá su propia lógica
     */
    public abstract String getTipoCuenta();

    /**
     * Método abstracto - cada tipo de cuenta puede tener límites diferentes
     */
    public abstract double getLimiteRetiroDiario();

    /**
     * Implementación de ITransaccionable
     */
    @Override
    public boolean depositar(double monto) {
        if (monto <= 0) {
            return false;
        }
        if (!activa) {
            return false;
        }
        this.saldo += monto;
        return true;
    }

    /**
     * Implementación de ITransaccionable
     */
    @Override
    public boolean retirar(double monto) {
        if (monto <= 0 || !activa) {
            return false;
        }
        if (this.saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    /**
     * Implementación de ITransaccionable
     */
    @Override
    public double consultarSaldo() {
        return this.saldo;
    }

    /**
     * Implementación de ITransaccionable
     */
    @Override
    public boolean estaActiva() {
        return this.activa;
    }

    /**
     * Implementación de IReporteable
     */
    @Override
    public String generarReporte() {
        return String.format(java.util.Locale.US, "Cuenta: %s | Tipo: %s | Titular: %s | Saldo: $%.2f",
                numeroCuenta, getTipoCuenta(), titularNombre, saldo);
    }

    /**
     * Implementación de IReporteable
     */
    @Override
    public String generarReporteDetallado() {
        return String.format(
                "=== REPORTE DE CUENTA ===\n" +
                        "Número de Cuenta: %s\n" +
                        "Tipo: %s\n" +
                        "Titular: %s\n" +
                        "Cédula: %s\n" +
                        "Saldo Actual: $%.2f\n" +
                        "Fecha Apertura: %s\n" +
                        "Estado: %s\n" +
                        "Límite Retiro Diario: $%.2f",
                numeroCuenta, getTipoCuenta(), titularNombre, titularCedula,
                saldo, fechaApertura, (activa ? "ACTIVA" : "INACTIVA"), getLimiteRetiroDiario()
        );
    }

    /**
     * Implementación de IReporteable
     */
    @Override
    public String obtenerFechaUltimoReporte() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    // Getters y Setters
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getTitularCedula() {
        return titularCedula;
    }

    public void setTitularCedula(String titularCedula) {
        this.titularCedula = titularCedula;
    }

    public String getTitularNombre() {
        return titularNombre;
    }

    public void setTitularNombre(String titularNombre) {
        this.titularNombre = titularNombre;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | Saldo: $%.2f",
                numeroCuenta, getTipoCuenta(), titularNombre, saldo);
    }
}