package co.uquindio.edu.co.poryectofinalp1.Model;

public class Banco {
    private String nombre;
    private String id;

    public Banco() {}

    public Banco(String id, String nombre) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return  nombre + id;
    }
}
