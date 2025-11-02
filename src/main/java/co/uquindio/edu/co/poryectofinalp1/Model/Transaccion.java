package co.uquindio.edu.co.poryectofinalp1.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    private String tipo; // "DEPOSITO", "RETIRO", "TRANSFERENCIA"
    private String cuentaOrigen;
    private String cuentaDestino;
    private double monto;
    private LocalDateTime fecha;

    public Transaccion() {}

    public Transaccion(String tipo, String cuentaOrigen, String cuentaDestino, double monto) {
        this.tipo = tipo;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    // Constructor para depósitos y retiros (sin cuenta destino)
    public Transaccion(String tipo, String cuentaOrigen, double monto) {
        this(tipo, cuentaOrigen, null, monto);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(String cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public String getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(String cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = fecha.format(formatter);

        if (cuentaDestino != null && !cuentaDestino.isEmpty()) {
            return tipo + " | Origen: " + cuentaOrigen + " → Destino: " + cuentaDestino +
                    " | Monto: " + String.format("$%.2f", monto) + " | " + fechaFormateada;
        } else {
            return tipo + " | Cuenta: " + cuentaOrigen + " | Monto: " +
                    String.format("$%.2f", monto) + " | " + fechaFormateada;
        }
    }
}