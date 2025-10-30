package co.uquindio.edu.co.poryectofinalp1.Repositorio;

import co.uquindio.edu.co.poryectofinalp1.Model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListClientes {

        private static ListClientes isntancia;
        private List<Cliente> cliente;
        private ListClientes() {
            cliente = new ArrayList<Cliente>();
        }

        /**

         Aplicamos Singleton
         @return*/
        public static ListClientes getIsntancia() {
            if (isntancia == null) {
                isntancia = new ListClientes();}
            return isntancia;}
        public List<Cliente> getClientes() {
            return cliente;}
        public void addCliente(Cliente cliente) {
            this.cliente.add(cliente);}
        public void removeCliente(Cliente cliente) {
            this.cliente.remove(cliente);}
        public Cliente buscarCliente(String cedula) {
            return this.cliente.stream().filter(i -> i.getCedula() == cedula).findFirst().orElse(null);

        }
    }

