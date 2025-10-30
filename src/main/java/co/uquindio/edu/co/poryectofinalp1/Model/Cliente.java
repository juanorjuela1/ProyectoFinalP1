package co.uquindio.edu.co.poryectofinalp1.Model;

public class Cliente {
   private String nombre;
   private String cedula;
   private double saldo;

   public Cliente() {}

    public Cliente(String nombre, String cedula, double saldo) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.saldo = saldo;
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
}
