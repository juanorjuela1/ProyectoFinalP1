package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.*;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListCliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ClienteController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField cedulaField;

    @FXML
    private TextField saldoInicialField;

    @FXML
    private ComboBox<String> tipoCuentaCombo;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> nombreColumn;

    @FXML
    private TableColumn<Cliente, String> cedulaColumn;

    @FXML
    private TableColumn<Cliente, String> numeroCuentaColumn;

    @FXML
    private TableColumn<Cliente, String> saldoColumn;

    private ListCliente lista;
    private ObservableList<Cliente> clientesObservable;

    public void initialize() {
        lista = ListCliente.getInstance();
        clientesObservable = FXCollections.observableArrayList(lista.getClientes());

        // Configurar las columnas de la tabla
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        numeroCuentaColumn.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));

        // Configurar columna de saldo con formato
        saldoColumn.setCellValueFactory(cellData -> {
            double saldo = cellData.getValue().getSaldo();
            return new javafx.beans.property.SimpleStringProperty(
                    String.format("$%.2f", saldo)
            );
        });

        tablaClientes.setItems(clientesObservable);
        tipoCuentaCombo.getSelectionModel().selectFirst();
    }

    private boolean validarCampos() {
        if (nombreField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El nombre es obligatorio", Alert.AlertType.WARNING);
            return false;
        }
        if (cedulaField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "La cédula es obligatoria", Alert.AlertType.WARNING);
            return false;
        }
        if (saldoInicialField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El saldo inicial es obligatorio", Alert.AlertType.WARNING);
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

    @FXML
    private void onCrearCliente() {
        if (!validarCampos()) {
            return;
        }
        try {
            String nombre = nombreField.getText().trim();
            String cedula = cedulaField.getText().trim();
            double saldo = Double.parseDouble(saldoInicialField.getText().trim());
            String tipoCuenta = tipoCuentaCombo.getValue();

            if (tipoCuenta == null) {
                mostrarAlerta("Error", "Debe seleccionar un tipo de cuenta", Alert.AlertType.WARNING);
                return;
            }

            Cliente clienteExistente = lista.buscarClientePorCedula(cedula);
            if (clienteExistente != null) {
                mostrarAlerta("Error",
                        "Ya existe una cuenta con esa cédula\n" +
                                "Número de cuenta: " + clienteExistente.getNumeroCuenta(),
                        Alert.AlertType.ERROR);
                return;
            }

            if (saldo < 0) {
                mostrarAlerta("Error", "El saldo inicial no puede ser negativo", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = new Cliente(nombre, cedula);

            CuentaBancaria cuenta;
            switch (tipoCuenta) {
                case "AHORRO":
                    cuenta = new CuentaAhorro(nombre, cedula, saldo);
                    break;
                case "CORRIENTE":
                    cuenta = new CuentaCorriente(nombre, cedula, saldo);
                    break;
                case "EMPRESARIAL":
                    cuenta = new CuentaEmpresarial(nombre, cedula, saldo);
                    break;
                default:
                    cuenta = new CuentaAhorro(nombre, cedula, saldo);
            }

            cliente.agregarCuenta(cuenta);
            lista.addCliente(cliente);

            mostrarAlerta("¡Cuenta creada exitosamente!",
                    "Tipo de cuenta: " + tipoCuenta + "\n" +
                            "Número de cuenta: " + cuenta.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre() + "\n" +
                            "Cédula: " + cliente.getCedula() + "\n" +
                            "Saldo inicial: $" + String.format("%.2f", cuenta.getSaldo()),
                    Alert.AlertType.INFORMATION);

            nombreField.clear();
            cedulaField.clear();
            saldoInicialField.clear();
            tipoCuentaCombo.getSelectionModel().selectFirst();

            clientesObservable.clear();
            clientesObservable.addAll(lista.getClientes());

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El saldo debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlMenu() throws IOException {
        Parent inicio;
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/BancoView.fxml"));
        inicio = loader.load();
        HelloApplication.getPrimaryStage().getScene().setRoot(inicio);
    }

    @FXML
    private void onCancelar() throws IOException {
        volverAlMenu();
    }
}