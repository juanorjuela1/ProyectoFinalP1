package co.uquindio.edu.co.poryectofinalp1.Model;

public class Empleado {
    private String nombre;
    private String cedula;
    private double salario;

    public Empleado() {}

    public Empleado(String nombre, String cedula, double salario) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.salario = salario;
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

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "nombre='" + nombre + '\'' + ", cedula='" + cedula + '\'' + ", salario=" + String.format("$%.2f", salario);
    }
}