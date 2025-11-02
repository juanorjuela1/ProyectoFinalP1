package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Empleado;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class EmpleadoController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField cedulaField;

    @FXML
    private TextField salarioField;

    @FXML
    private TableView<Empleado> tablaEmpleados;

    @FXML
    private TableColumn<Empleado, String> nombreColumn;

    @FXML
    private TableColumn<Empleado, String> cedulaColumn;

    @FXML
    private TableColumn<Empleado, Double> salarioColumn;

    private ListEmpleado lista;
    private ObservableList<Empleado> empleadosObservable;

    public void initialize() {
        lista = ListEmpleado.getInstancia();
        empleadosObservable = FXCollections.observableArrayList(lista.getEmpleados());

        // Configurar las columnas de la tabla
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        salarioColumn.setCellValueFactory(new PropertyValueFactory<>("salario"));

        tablaEmpleados.setItems(empleadosObservable);
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
        if (salarioField.getText().trim().isEmpty()) {
            mostrarAlerta("Error de validación", "El salario es obligatorio", Alert.AlertType.WARNING);
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
        nombreField.clear();
        cedulaField.clear();
        salarioField.clear();
    }

    @FXML
    private void registrarEmpleado() {
        if (!validarCampos()) {
            return;
        }

        try {
            String nombre = nombreField.getText().trim();
            String cedula = cedulaField.getText().trim();
            double salario = Double.parseDouble(salarioField.getText().trim());

            // Verificar si el empleado ya existe
            if (lista.buscarEmpleado(cedula) != null) {
                mostrarAlerta("Error", "Ya existe un empleado con esa cédula", Alert.AlertType.ERROR);
                return;
            }

            Empleado empleado = new Empleado(nombre, cedula, salario);
            lista.addEmpleado(empleado);
            empleadosObservable.add(empleado);

            mostrarAlerta("Éxito", "Empleado registrado correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El salario debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void buscarEmpleado() {
        String cedula = cedulaField.getText().trim();

        if (cedula.isEmpty()) {
            mostrarAlerta("Error", "Ingrese una cédula para buscar", Alert.AlertType.WARNING);
            return;
        }

        Empleado empleado = lista.buscarEmpleado(cedula);

        if (empleado != null) {
            nombreField.setText(empleado.getNombre());
            salarioField.setText(String.valueOf(empleado.getSalario()));
            mostrarAlerta("Empleado encontrado", empleado.toString(), Alert.AlertType.INFORMATION);
        } else {
            mostrarAlerta("No encontrado", "No existe un empleado con esa cédula", Alert.AlertType.WARNING);
            limpiarCampos();
        }
    }

    @FXML
    private void actualizarEmpleado() {
        if (!validarCampos()) {
            return;
        }

        try {
            String cedula = cedulaField.getText().trim();
            String nombre = nombreField.getText().trim();
            double salario = Double.parseDouble(salarioField.getText().trim());

            Empleado empleadoActualizado = new Empleado(nombre, cedula, salario);

            if (lista.actualizarEmpleado(cedula, empleadoActualizado)) {
                // Actualizar la tabla
                tablaEmpleados.refresh();
                mostrarAlerta("Éxito", "Empleado actualizado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se encontró el empleado con esa cédula", Alert.AlertType.ERROR);
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El salario debe ser un valor numérico válido", Alert.AlertType.ERROR);
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