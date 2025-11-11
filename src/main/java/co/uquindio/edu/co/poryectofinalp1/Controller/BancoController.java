package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import co.uquindio.edu.co.poryectofinalp1.Repositorio.SesionUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class BancoController {

        @FXML
        private Button btnAcceder;

        @FXML
        private Button btnCrearCuenta;

        @FXML
        private Button btnSalir;

        @FXML
        private Button btnTransferencia;

        @FXML
        private Button btnCerrarSesion;

        @FXML
        private Label lblBienvenida;

        private SesionUsuario sesion;

        public void initialize() {
                sesion = SesionUsuario.getInstancia();
                configurarVistaPorRol();
        }

        /**
         * Configura qué botones se muestran según el rol del usuario
         */
        private void configurarVistaPorRol() {
                if (sesion.getUsuarioActual() != null) {
                        String nombreUsuario = sesion.getUsuarioActual().getUsername();
                        String tipoUsuario = sesion.getUsuarioActual().getTipo().toString();
                        lblBienvenida.setText("Bienvenido: " + nombreUsuario + " (" + tipoUsuario + ")");

                        System.out.println("Configurando vista para rol: " + tipoUsuario);

                        if (sesion.esCliente()) {
                                // Los clientes solo ven Transferencias
                                System.out.println("Configurando vista de CLIENTE");
                                btnAcceder.setVisible(false);
                                btnAcceder.setManaged(false);
                                btnCrearCuenta.setVisible(false);
                                btnCrearCuenta.setManaged(false);
                                btnTransferencia.setVisible(true);
                                btnTransferencia.setManaged(true);
                        } else if (sesion.esAdmin()) {
                                // Los admins ven todas las funciones
                                System.out.println("Configurando vista de ADMIN");
                                btnAcceder.setVisible(true);
                                btnAcceder.setManaged(true);
                                btnCrearCuenta.setVisible(true);
                                btnCrearCuenta.setManaged(true);
                                btnTransferencia.setVisible(true);
                                btnTransferencia.setManaged(true);
                        } else if (sesion.esCajero()) {
                                // Los cajeros solo ven Crear Cuenta y Transacciones
                                System.out.println("Configurando vista de CAJERO");
                                btnAcceder.setVisible(false);
                                btnAcceder.setManaged(false);
                                btnCrearCuenta.setVisible(true);
                                btnCrearCuenta.setManaged(true);
                                btnTransferencia.setVisible(true);
                                btnTransferencia.setManaged(true);
                        }
                }
        }

        @FXML
        void onAbrirTransferencia(ActionEvent event) {
                try {
                        Parent transaccionView;
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/TransaccionView.fxml"));
                        transaccionView = loader.load();
                        HelloApplication.getPrimaryStage().getScene().setRoot(transaccionView);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void onAcceder(ActionEvent event) {
                try {
                        Parent gestionView;
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/EmpleadoView.fxml"));
                        gestionView = loader.load();
                        HelloApplication.getPrimaryStage().getScene().setRoot(gestionView);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void onCrearCliente(ActionEvent event) {
                try {
                        Parent clienteView;
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/ClienteView.fxml"));
                        clienteView = loader.load();
                        HelloApplication.getPrimaryStage().getScene().setRoot(clienteView);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void onCerrarSesion(ActionEvent event) {
                sesion.cerrarSesion();
                try {
                        Parent loginView;
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/co/uquindio/edu/co/poryectofinalp1/LoginView.fxml"));
                        loginView = loader.load();
                        HelloApplication.getPrimaryStage().getScene().setRoot(loginView);
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        @FXML
        void onSalir(ActionEvent event) {
                System.exit(0);
        }
}