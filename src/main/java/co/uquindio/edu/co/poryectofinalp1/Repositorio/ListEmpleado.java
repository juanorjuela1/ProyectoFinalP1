package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Empleado;

import java.util.ArrayList;
import java.util.List;

public class ListEmpleado {

    private static ListEmpleado instancia;
    private List<Empleado> empleados;

    private ListEmpleado() {
        empleados = new ArrayList<Empleado>();
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

    public void removeEmpleado(Empleado empleado) {
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
