package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * Cliente - HERENCIA de Persona
 * Representa a una persona que TIENE una cuenta bancaria (COMPOSICIÓN)
 * Aplica: HERENCIA + COMPOSICIÓN + POLIMORFISMO + ENCAPSULAMIENTO
 */
public class Cliente extends Persona {
    private String numeroCuenta;
    private double saldo;
    private String fechaRegistro;
    private boolean activo;
    private static int contadorCuentas = 0;

    public Cliente() {
        super();
        this.activo = true;
    }

    public Cliente(String nombre, String cedula, double saldo) {
        super(nombre, cedula);
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = saldo;
        this.activo = true;
    }

    public Cliente(String nombre, String cedula, String telefono, String email, double saldo) {
        super(nombre, cedula, telefono, email);
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = saldo;
        this.activo = true;
    }

    /**
     * Genera un número de cuenta único automáticamente
     */
    private String generarNumeroCuenta() {
        contadorCuentas++;
        return String.format("%04d", contadorCuentas);
    }

    /**
     * POLIMORFISMO - Implementación específica para Cliente
     */
    @Override
    public String obtenerInformacion() {
        return "Cliente: " + nombre + " | CC: " + cedula +
                " | Cuenta: " + numeroCuenta;
    }

    /**
     * POLIMORFISMO - Implementación específica para Cliente
     */
    @Override
    public String obtenerRol() {
        return "CLIENTE";
    }

    // Métodos específicos de Cliente
    public double consultarSaldo() {
        return this.saldo;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            this.saldo += monto;
        }
    }

    public boolean retirar(double monto) {
        if (monto > 0 && this.saldo >= monto) {
            this.saldo -= monto;
            return true;
        }
        return false;
    }

    // Getters y Setters específicos
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

    @Override
    public String toString() {
        return "Cuenta: " + numeroCuenta + " | " +
                nombre + " | CC: " + cedula + " | Saldo: " + String.format("$%.2f", saldo);
    }
}