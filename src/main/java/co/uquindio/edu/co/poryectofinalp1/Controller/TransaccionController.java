package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Cliente;
import co.uquindio.edu.co.poryectofinalp1.Model.Transaccion;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListCliente;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListTransaccion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class TransaccionController {

    @FXML
    private TextField cuentaOrigenField;

    @FXML
    private TextField cuentaDestinoField;

    @FXML
    private TextField montoField;

    @FXML
    private TableView<Cliente> tablaCuentas;

    @FXML
    private TableColumn<Cliente, String> nombreClienteColumn;

    @FXML
    private TableColumn<Cliente, String> cedulaClienteColumn;

    @FXML
    private TableColumn<Cliente, String> numeroCuentaColumn;

    @FXML
    private TableColumn<Cliente, String> tipoCuentaColumn;

    private ListCliente listaClientes;
    private ListTransaccion listaTransacciones;
    private ObservableList<Cliente> clientesObservable;

    public void initialize() {
        listaClientes = ListCliente.getInstance();
        listaTransacciones = ListTransaccion.getInstancia();
        clientesObservable = FXCollections.observableArrayList(listaClientes.getClientes());

        nombreClienteColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cedulaClienteColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        numeroCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));

        tipoCuentaColumn.setCellValueFactory(cellData -> {
            Cliente cliente = cellData.getValue();
            String tipoCuenta = cliente.getCuentaPrincipal() != null ?
                    cliente.getCuentaPrincipal().getTipoCuenta() : "N/A";
            return new javafx.beans.property.SimpleStringProperty(tipoCuenta);
        });

        tablaCuentas.setItems(clientesObservable);
    }

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

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        cuentaOrigenField.clear();
        cuentaDestinoField.clear();
        montoField.clear();
    }

    @FXML
    private void mostrarCuentas() {
        clientesObservable.clear();
        clientesObservable.addAll(listaClientes.getClientes());

        tablaCuentas.setVisible(true);
        tablaCuentas.setManaged(true);
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

            cliente.setSaldo(cliente.getSaldoTotal() + monto);

            Transaccion transaccion = new Transaccion("DEPOSITO", numeroCuentaOrigen, monto);
            listaTransacciones.addTransaccion(transaccion);

            mostrarAlerta("Éxito",
                    "Depósito realizado correctamente\n" +
                            "Cuenta: " + cliente.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre(),
                    Alert.AlertType.INFORMATION);

            limpiarCampos();
            if (tablaCuentas.isVisible()) {
                tablaCuentas.refresh();
            }

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

            cliente.setSaldo(cliente.getSaldo() - monto);

            Transaccion transaccion = new Transaccion("RETIRO", numeroCuentaOrigen, monto);
            listaTransacciones.addTransaccion(transaccion);

            mostrarAlerta("Éxito",
                    "Retiro realizado correctamente\n" +
                            "Cuenta: " + cliente.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre(),
                    Alert.AlertType.INFORMATION);

            limpiarCampos();
            if (tablaCuentas.isVisible()) {
                tablaCuentas.refresh();
            }

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

            clienteOrigen.setSaldo(clienteOrigen.getSaldo() - monto);
            clienteDestino.setSaldo(clienteDestino.getSaldo() + monto);

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
            if (tablaCuentas.isVisible()) {
                tablaCuentas.refresh();
            }

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