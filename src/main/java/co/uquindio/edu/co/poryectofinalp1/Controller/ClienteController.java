package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Cliente;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListCliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ClienteController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField cedulaField;

    @FXML
    private TextField saldoInicialField;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button btnCancelar;

    @FXML
    private ListView<Cliente> listaClientes;

    private ListCliente lista;

    public void initialize() {
        lista = ListCliente.getInstance();
        // Cargar clientes existentes en la lista
        listaClientes.getItems().addAll(lista.getClientes());
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

            // Verificar si ya existe un cliente con esa cédula
            Cliente clienteExistente = lista.buscarClientePorCedula(cedula);
            if (clienteExistente != null) {
                mostrarAlerta("Error",
                        "Ya existe una cuenta con esa cédula\n" +
                                "Número de cuenta: " + clienteExistente.getCuentas(),
                        Alert.AlertType.ERROR);
                return;
            }

            // Validar que el saldo inicial sea positivo
            if (saldo < 0) {
                mostrarAlerta("Error", "El saldo inicial no puede ser negativo", Alert.AlertType.ERROR);
                return;
            }

            Cliente cliente = new Cliente(nombre, cedula, saldo);
            lista.addCliente(cliente);

            // Mostrar mensaje con el número de cuenta generado
            mostrarAlerta("¡Cuenta creada exitosamente!",
                    "Se ha generado la cuenta N° " + cliente.getCuentas() + "\n" +
                            "Titular: " + cliente.getNombre() + "\n" +
                            "Cédula: " + cliente.getCedula() + "\n" +
                            "Saldo inicial: $" + String.format("%.2f", cliente.getSaldoTotal()),
                    Alert.AlertType.INFORMATION);

            // Limpiar campos
            nombreField.clear();
            cedulaField.clear();
            saldoInicialField.clear();

            // Agregar a la lista visual
            listaClientes.getItems().add(cliente);

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