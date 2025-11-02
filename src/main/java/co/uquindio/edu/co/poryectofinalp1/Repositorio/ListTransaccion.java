package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Transaccion;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListTransaccion {

    private static ListTransaccion instancia;
    private List<Transaccion> transacciones;

    private ListTransaccion() {
        transacciones = new ArrayList<Transaccion>();
    }

    /**
     * Aplicamos Singleton
     * @return instancia única de ListTransacciones
     */
    public static ListTransaccion getInstancia() {
        if (instancia == null) {
            instancia = new ListTransaccion();
        }
        return instancia;
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public void addTransaccion(Transaccion transaccion) {
        this.transacciones.add(transaccion);
    }

    public void removeTransaccion(Transaccion transaccion) {
        this.transacciones.remove(transaccion);
    }

    /**
     * Busca todas las transacciones de una cuenta específica
     */
    public List<Transaccion> buscarTransaccionesPorCuenta(String cedula) {
        return this.transacciones.stream()
                .filter(t -> t.getCuentaOrigen().equals(cedula) ||
                        (t.getCuentaDestino() != null && t.getCuentaDestino().equals(cedula)))
                .collect(Collectors.toList());
    }

    /**
     * Busca transacciones por tipo
     */
    public List<Transaccion> buscarTransaccionesPorTipo(String tipo) {
        return this.transacciones.stream()
                .filter(t -> t.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }
}