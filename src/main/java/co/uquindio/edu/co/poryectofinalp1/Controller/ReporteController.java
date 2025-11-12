package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Reporte;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.SesionUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ReporteController {

    @FXML
    private TextArea reporteArea;

    @FXML
    private TextField numeroCuentaField;

    @FXML
    private TextField montoSospechosoField;

    @FXML
    private VBox seccionAdmin;

    @FXML
    private Button btnLimpiar;

    private Reporte generadorReportes;
    private SesionUsuario sesion;

    public void initialize() {
        generadorReportes = Reporte.getInstancia();
        sesion = SesionUsuario.getInstancia();

        // Configurar visibilidad según el rol
        configurarVistaPorRol();
    }

    /**
     * Configura qué secciones se muestran según el rol del usuario
     */
    private void configurarVistaPorRol() {
        if (sesion.getUsuarioActual() != null) {
            // Solo los ADMIN ven la sección de reportes administrativos
            if (sesion.esAdmin()) {
                seccionAdmin.setVisible(true);
                seccionAdmin.setManaged(true);
            } else {
                seccionAdmin.setVisible(false);
                seccionAdmin.setManaged(false);
            }
        }
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
     * Muestra el reporte en el TextArea
     */
    private void mostrarReporte(String reporte) {
        if (reporte != null && !reporte.isEmpty()) {
            reporteArea.setText(reporte);
        } else {
            reporteArea.setText("No hay datos para mostrar.");
        }
    }

    @FXML
    private void generarReporteConsolidado() {
        String reporte = generadorReportes.generarReporteConsolidado();
        mostrarReporte(reporte);
    }

    @FXML
    private void generarReporteTransacciones() {
        String reporte = generadorReportes.generarReporteTransacciones();
        mostrarReporte(reporte);
    }

    @FXML
    private void generarReporteCliente() {
        String numeroCuenta = numeroCuentaField.getText().trim();

        if (numeroCuenta.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese un número de cuenta", Alert.AlertType.WARNING);
            return;
        }

        String reporte = generadorReportes.generarReporteTransaccionesPorCliente(numeroCuenta);
        mostrarReporte(reporte);

        // Limpiar el campo
        numeroCuentaField.clear();
    }

    @FXML
    private void generarReporteClientes() {
        String reporte = generadorReportes.generarReporteClientes();
        mostrarReporte(reporte);
    }

    @FXML
    private void generarReporteEmpleados() {
        if (!sesion.esAdmin()) {
            mostrarAlerta("Acceso Denegado",
                    "Solo los administradores pueden ver este reporte",
                    Alert.AlertType.ERROR);
            return;
        }

        String reporte = generadorReportes.generarReporteEmpleados();
        mostrarReporte(reporte);
    }

    @FXML
    private void generarReporteSospechosas() {
        if (!sesion.esAdmin()) {
            mostrarAlerta("Acceso Denegado",
                    "Solo los administradores pueden ver este reporte",
                    Alert.AlertType.ERROR);
            return;
        }

        String montoTexto = montoSospechosoField.getText().trim();

        if (montoTexto.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese un monto para filtrar operaciones sospechosas",
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            double monto = Double.parseDouble(montoTexto);

            if (monto <= 0) {
                mostrarAlerta("Error", "El monto debe ser mayor a 0", Alert.AlertType.ERROR);
                return;
            }

            String reporte = generadorReportes.generarReporteOperacionesSospechosas(monto);
            mostrarReporte(reporte);

            // Limpiar el campo
            montoSospechosoField.clear();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El monto debe ser un valor numérico válido", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limpiarReporte() {
        reporteArea.clear();
    }

    @FXML
    private void volverAlMenu() throws IOException {
        Parent inicio;
        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/BancoView.fxml")
        );
        inicio = loader.load();
        HelloApplication.getPrimaryStage().getScene().setRoot(inicio);
    }
}