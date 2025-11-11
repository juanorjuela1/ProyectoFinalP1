package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Cliente;
import co.uquindio.edu.co.poryectofinalp1.Model.Transaccion;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListCliente;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListTransaccion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TransaccionController {

    @FXML
    private TextField cuentaOrigenField;

    @FXML
    private TextField cuentaDestinoField;

    @FXML
    private TextField montoField;

    @FXML
    private ListView<Cliente> listaCuentas;

    private ListCliente listaClientes;
    private ListTransaccion listaTransacciones;

    public void initialize() {
        listaClientes = ListCliente.getInstance();
        listaTransacciones = ListTransaccion.getInstancia();

        mostrarCuentas();
    }

    /**
     * Valida que los campos necesarios estén completos
     */
    private boolean validarCamposBasicos() {
        if (cuentaOrigenField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La cuenta origen es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (montoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El monto es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Muestra una alerta al usuario
     */
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Limpia los campos del formulario
     */
    private void limpiarCampos() {
        cuentaOrigenField.clear();
        cuentaDestinoField.clear();
        montoField.clear();
    }

    @FXML
    private void mostrarCuentas() {
        listaCuentas.setItems(FXCollections.observableArrayList(listaClientes.getClientes()));
        listaCuentas.setVisible(true);
        listaCuentas.setManaged(true);
    }

    @FXML
    private void realizarDeposito() {
        if (!validarCamposBasicos()) {
            return;
        }

        try {
            String numeroCuentaOrigen = cuentaOrigenField.getText().trim();
            double monto = Double.parseDouble(montoField.getText().trim());

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = listaClientes.buscarClientePorNumeroCuenta(numeroCuentaOrigen);

            if (cliente == null) {
                mostrarAlerta("Error", "No existe una cuenta con ese número", Alert.AlertType.ERROR);
                return;
            }

            // Realizar depósito
            cliente.setSaldo(cliente.getSaldoTotal() + monto);

            // Registrar transacción
            Transaccion transaccion = new Transaccion("DEPOSITO", numeroCuentaOrigen, monto);
            listaTransacciones.addTransaccion(transaccion);

            mostrarAlerta("Éxito",
                    "Depósito realizado correctamente\n" +
                            "Cuenta: " + cliente.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre() + "\n" +
                            "Nuevo saldo: $" + String.format("%.2f", cliente.getSaldo()),
                    Alert.AlertType.INFORMATION);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void realizarRetiro() {
        if (!validarCamposBasicos()) {
            return;
        }

        try {
            String numeroCuentaOrigen = cuentaOrigenField.getText().trim();
            double monto = Double.parseDouble(montoField.getText().trim());

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = listaClientes.buscarClientePorNumeroCuenta(numeroCuentaOrigen);

            if (cliente == null) {
                mostrarAlerta("Error", "No existe una cuenta con ese número", Alert.AlertType.ERROR);
                return;
            }

            if (cliente.getSaldo() < monto) {
                mostrarAlerta("Error",
                        "Saldo insuficiente para realizar el retiro\n" +
                                "Saldo disponible: $" + String.format("%.2f", cliente.getSaldo()),
                        Alert.AlertType.ERROR);
                return;
            }

            // Realizar retiro
            cliente.setSaldo(cliente.getSaldo() - monto);

            // Registrar transacción
            Transaccion transaccion = new Transaccion("RETIRO", numeroCuentaOrigen, monto);
            listaTransacciones.addTransaccion(transaccion);

            mostrarAlerta("Éxito",
                    "Retiro realizado correctamente\n" +
                            "Cuenta: " + cliente.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre() + "\n",
                    Alert.AlertType.INFORMATION);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void realizarTransferencia() {
        if (!validarCamposBasicos()) {
            return;
        }

        if (cuentaDestinoField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La cuenta destino es obligatoria para transferencias", Alert.AlertType.WARNING);
            return;
        }

        try {
            String numeroCuentaOrigen = cuentaOrigenField.getText().trim();
            String numeroCuentaDestino = cuentaDestinoField.getText().trim();
            double monto = Double.parseDouble(montoField.getText().trim());

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0", Alert.AlertType.ERROR);
                return;
            }

            if (numeroCuentaOrigen.equals(numeroCuentaDestino)) {
                mostrarAlerta("Error", "No puede transferir a la misma cuenta", Alert.AlertType.ERROR);
                return;
            }

            Cliente clienteOrigen = listaClientes.buscarClientePorNumeroCuenta(numeroCuentaOrigen);
            Cliente clienteDestino = listaClientes.buscarClientePorNumeroCuenta(numeroCuentaDestino);

            if (clienteOrigen == null) {
                mostrarAlerta("Error", "No existe una cuenta origen con ese número", Alert.AlertType.ERROR);
                return;
            }

            if (clienteDestino == null) {
                mostrarAlerta("Error", "No existe una cuenta destino con ese número", Alert.AlertType.ERROR);
                return;
            }

            if (clienteOrigen.getSaldo() < monto) {
                mostrarAlerta("Error",
                        "Saldo insuficiente para realizar la transferencia\n" +
                                "Saldo disponible: $" + String.format("%.2f", clienteOrigen.getSaldo()),
                        Alert.AlertType.ERROR);
                return;
            }

            // Realizar transferencia
            clienteOrigen.setSaldo(clienteOrigen.getSaldo() - monto);
            clienteDestino.setSaldo(clienteDestino.getSaldo() + monto);

            // Registrar transacción
            Transaccion transaccion = new Transaccion("TRANSFERENCIA", numeroCuentaOrigen, numeroCuentaDestino, monto);
            listaTransacciones.addTransaccion(transaccion);

            mostrarAlerta("Éxito",
                    "Transferencia realizada correctamente\n\n" +
                            "De: " + clienteOrigen.getNombre() + " (" + numeroCuentaOrigen + ")\n" +
                            "Para: " + clienteDestino.getNombre() + " (" + numeroCuentaDestino + ")\n" +
                            "Monto: $" + String.format("%.2f", monto) + "\n\n" +
                            "Saldo origen: $" + String.format("%.2f", clienteOrigen.getSaldo()) + "\n" +
                            "Saldo destino: $" + String.format("%.2f", clienteDestino.getSaldo()),
                    Alert.AlertType.INFORMATION);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlMenu() throws IOException {
        Parent inicio;
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/BancoView.fxml"));
        inicio = loader.load();
        HelloApplication.getPrimaryStage().getScene().setRoot(inicio);
    }
}