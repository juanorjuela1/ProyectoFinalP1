package co.uquindio.edu.co.poryectofinalp1.Model;

public class Cliente {
    private String numeroCuenta;
    private String nombre;
    private String cedula;
    private double saldo;
    private static int contadorCuentas = 0; // Comienza en 1000

    public Cliente() {}

    public Cliente(String nombre, String cedula, double saldo) {
        this.numeroCuenta = generarNumeroCuenta();
        this.nombre = nombre;
        this.cedula = cedula;
        this.saldo = saldo;
    }

    /**
     * Genera un número de cuenta único automáticamente
     */
    private String generarNumeroCuenta() {
        contadorCuentas++;
        return String.format("%04d", contadorCuentas);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "Cuenta: " + numeroCuenta + " | " +
                nombre + " | CC: " + cedula + " | Saldo: " + String.format("$%.2f", saldo);
    }
}