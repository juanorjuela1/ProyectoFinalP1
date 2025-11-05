package co.uquindio.edu.co.poryectofinalp1.Model;

/**
 * Clase abstracta Persona - Representa a cualquier persona en el sistema
 * Implementa ABSTRACCIÓN y ENCAPSULAMIENTO
 * Es la clase base para Cliente, Empleado, etc.
 */
public abstract class Persona {
    protected String nombre;
    protected String cedula;
    protected String telefono;
    protected String email;
    protected String direccion;

    public Persona() {}

    public Persona(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public Persona(String nombre, String cedula, String telefono, String email) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.email = email;
    }

    /**
     * Método abstracto - cada tipo de persona tendrá su propia información
     */
    public abstract String obtenerInformacion();

    /**
     * Método abstracto - cada tipo de persona tendrá su propio rol
     */
    public abstract String obtenerRol();

    // Getters y Setters - ENCAPSULAMIENTO
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return nombre + " | CC: " + cedula;
    }
}