package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Model.Usuario;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.ListUsuario;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.SesionUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private ListUsuario listaUsuarios;
    private SesionUsuario sesion;

    public void initialize() {
        listaUsuarios = ListUsuario.getInstancia();
        sesion = SesionUsuario.getInstancia();
    }

    @FXML
    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese usuario y contraseña", Alert.AlertType.WARNING);
            return;
        }

        Usuario usuario = listaUsuarios.autenticar(username, password);

        if (usuario != null) {
            sesion.iniciarSesion(usuario);

            try {
                Parent bancoView;
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/BancoView.fxml"));
                bancoView = loader.load();
                HelloApplication.getPrimaryStage().getScene().setRoot(bancoView);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "No se pudo cargar la vista principal", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Error de autenticación", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
            passwordField.clear();
        }
    }

    @FXML
    private void onSalir() {
        System.exit(0);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}