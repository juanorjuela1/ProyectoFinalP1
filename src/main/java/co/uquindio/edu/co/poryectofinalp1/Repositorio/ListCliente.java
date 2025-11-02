package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Cliente;

import java.util.ArrayList;
import java.util.List;


public class ListCliente {
    private static ListCliente instance;
    private List<Cliente> cliente;

    private ListCliente() {
        cliente = new ArrayList<Cliente>();
    }

    /**
     * Aplicamos Singleton
     * @return instancia única de ListClientes
     */
    public static ListCliente getInstance() {
        if (instance == null) {
            instance = new ListCliente();
        }
        return instance;
    }

    public List<Cliente> getClientes() {
        return cliente;
    }

    public void addCliente(Cliente cliente) {
        this.cliente.add(cliente);
    }

    public void removeCliente(Cliente cliente) {
        this.cliente.remove(cliente);
    }

    public Cliente buscarCliente(String cedula) {
        return this.cliente.stream()
                .filter(i -> i.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public Cliente buscarClientePorCedula(String cedula) {
        return this.cliente.stream()
                .filter(i -> i.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public Cliente buscarClientePorNumeroCuenta(String numeroCuenta) {
        return this.cliente.stream()
                .filter(i -> i.getNumeroCuenta().equals(numeroCuenta))
                .findFirst()
                .orElse(null);
    }
}

