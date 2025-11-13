package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.*;

import java.util.ArrayList;
import java.util.List;

public class ListCliente {
    private static ListCliente instance;
    private List<Cliente> cliente;

    private ListCliente() {
        cliente = new ArrayList<Cliente>();
        inicializarDatosPrueba();
    }

    /**
     * Inicializa clientes de prueba
     */
    private void inicializarDatosPrueba() {
        // Cliente 1 - Cuenta de Ahorro
        Cliente cliente1 = new Cliente("Juan Pérez", "1234567890");
        CuentaAhorro cuenta1 = new CuentaAhorro("Juan Pérez", "1234567890", 1500000.0);
        cliente1.agregarCuenta(cuenta1);
        this.cliente.add(cliente1);

        // Cliente 2 - Cuenta Corriente
        Cliente cliente2 = new Cliente("María García", "0987654321");
        CuentaCorriente cuenta2 = new CuentaCorriente("María García", "0987654321", 2500000.0);
        cliente2.agregarCuenta(cuenta2);
        this.cliente.add(cliente2);

        // Cliente 3 - Cuenta Empresarial
        Cliente cliente3 = new Cliente("Tech Solutions SAS", "9001234567");
        CuentaEmpresarial cuenta3 = new CuentaEmpresarial("Tech Solutions SAS", "9001234567", 10000000.0);
        cliente3.agregarCuenta(cuenta3);
        this.cliente.add(cliente3);

        // Cliente 4 - Cuenta de Ahorro
        Cliente cliente4 = new Cliente("Carlos Rodríguez", "1122334455");
        CuentaAhorro cuenta4 = new CuentaAhorro("Carlos Rodríguez", "1122334455", 800000.0);
        cliente4.agregarCuenta(cuenta4);
        this.cliente.add(cliente4);

        // Cliente 5 - Cuenta Corriente
        Cliente cliente5 = new Cliente("Ana Martínez", "5544332211");
        CuentaCorriente cuenta5 = new CuentaCorriente("Ana Martínez", "5544332211", 3200000.0);
        cliente5.agregarCuenta(cuenta5);
        this.cliente.add(cliente5);

        System.out.println("✅ Se han cargado " + this.cliente.size() + " clientes de prueba");
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