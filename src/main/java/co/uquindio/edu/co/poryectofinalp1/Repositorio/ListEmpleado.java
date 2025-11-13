package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Empleado;

import java.util.ArrayList;
import java.util.List;

public class ListEmpleado {

    private static ListEmpleado instancia;
    private List<Empleado> empleados;

    private ListEmpleado() {
        empleados = new ArrayList<Empleado>();
        inicializarDatosPrueba();
    }

    /**
     * Inicializa empleados de prueba
     */
    private void inicializarDatosPrueba() {
        // Empleado 1 - Gerente
        Empleado emp1 = new Empleado("Pedro Sánchez", "1111111111", 4500000.0);
        this.empleados.add(emp1);

        // Empleado 2 - Cajero
        Empleado emp2 = new Empleado("Laura Gómez", "2222222222", 2500000.0);
        this.empleados.add(emp2);

        // Empleado 3 - Asesor
        Empleado emp3 = new Empleado("Miguel Ángel Torres", "3333333333", 3000000.0);
        this.empleados.add(emp3);

        // Empleado 4 - Cajera
        Empleado emp4 = new Empleado("Sofia Ramírez", "4444444444", 2500000.0);
        this.empleados.add(emp4);

        // Empleado 5 - Contador
        Empleado emp5 = new Empleado("Roberto Díaz", "5555555555", 3500000.0);
        this.empleados.add(emp5);

        System.out.println("✅ Se han cargado " + this.empleados.size() + " empleados de prueba");
    }

    /**
     * Aplicamos Singleton
     * @return instancia única de ListEmpleados
     */
    public static ListEmpleado getInstancia() {
        if (instancia == null) {
            instancia = new ListEmpleado();
        }
        return instancia;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }

    public void removeEmpleado(Empleado empleado)    {
        this.empleados.remove(empleado);
    }

    public Empleado buscarEmpleado(String cedula) {
        return this.empleados.stream()
                .filter(e -> e.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public boolean actualizarEmpleado(String cedula, Empleado empleadoActualizado) {
        Empleado empleado = buscarEmpleado(cedula);
        if (empleado != null) {
            empleado.setNombre(empleadoActualizado.getNombre());
            empleado.setSalario(empleadoActualizado.getSalario());
            return true;
        }
        return false;
    }
}
