package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.*;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListCliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

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
    private ListView<Cliente> listaClientes;

    private ListCliente lista;

    public void initialize() {
        lista = ListCliente.getInstance();
        listaClientes.getItems().addAll(lista.getClientes());

        tipoCuentaCombo.getSelectionModel().selectFirst();
    }

    /**
     * Valida que los campos del formulario estén completos
     */
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

            // Validar que se haya seleccionado un tipo de cuenta
            if (tipoCuenta == null) {
                mostrarAlerta("Error", "Debe seleccionar un tipo de cuenta", Alert.AlertType.WARNING);
                return;
            }

            // Verificar si ya existe un cliente con esa cédula
            Cliente clienteExistente = lista.buscarClientePorCedula(cedula);
            if (clienteExistente != null) {
                mostrarAlerta("Error",
                        "Ya existe una cuenta con esa cédula\n" +
                                "Número de cuenta: " + clienteExistente.getNumeroCuenta(),
                        Alert.AlertType.ERROR);
                return;
            }

            // Validar que el saldo inicial sea positivo
            if (saldo < 0) {
                mostrarAlerta("Error", "El saldo inicial no puede ser negativo", Alert.AlertType.ERROR);
                return;
            }

            // ✅ NUEVO: Crear el cliente y asignarle la cuenta según el tipo seleccionado
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

            // Mostrar mensaje con el número de cuenta generado
            mostrarAlerta("¡Cuenta creada exitosamente!",
                    "Tipo de cuenta: " + tipoCuenta + "\n" +
                            "Número de cuenta: " + cuenta.getNumeroCuenta() + "\n" +
                            "Titular: " + cliente.getNombre() + "\n" +
                            "Cédula: " + cliente.getCedula() + "\n" +
                            "Saldo inicial: $" + String.format("%.2f", cuenta.getSaldo()),
                    Alert.AlertType.INFORMATION);

            // Limpiar campos
            nombreField.clear();
            cedulaField.clear();
            saldoInicialField.clear();
            tipoCuentaCombo.getSelectionModel().selectFirst();

            // Actualizar la lista visual
            listaClientes.getItems().clear();
            listaClientes.getItems().addAll(lista.getClientes());

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