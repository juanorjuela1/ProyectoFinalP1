package co.uquindio.edu.co.poryectofinalp1.Controller;

import co.uquindio.edu.co.poryectofinalp1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

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
        void onCrearCuenta(ActionEvent event) {
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
        void onSalir(ActionEvent event) {
                System.exit(0);
        }
}


