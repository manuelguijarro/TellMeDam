package org.example.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.api.APICallback;
import org.example.App;
import org.example.api.UserAPIClient;
import org.example.model.Error;
import org.example.model.Log;

public class RegistroController {
    @FXML
    private Label mensajeAlerta;
    @FXML
    private TextField nombreUsuarioTextField;
    @FXML
    private TextField emailUsuarioTextField;
    @FXML
    private TextField contraseniaUsuarioTextField;
    private UserAPIClient userAPIClient;

    @FXML
    public void initialize() {
        mensajeAlerta.setVisible(false);

    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void registrarse() throws IOException, InterruptedException {
        Log log = new Log("");

        String mensaje = "";
        String nombreUsuario = nombreUsuarioTextField.getText();
        String emailUsuario = emailUsuarioTextField.getText();
        String contraseniaUsuario = contraseniaUsuarioTextField.getText();

        if (emailUsuario.isEmpty() || contraseniaUsuario.isEmpty())
            mensaje = ": Failed registration[campos vacios]: email o contraseña no pueden estar vacíos.";

        if (!emailUsuario.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"))
            mensaje = ": Failed registration[email]: '" + emailUsuario + "'" + " no es un email válido.";

        if (!contraseniaUsuario.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            mensaje = ": Failed registration[contraseña]: '" + contraseniaUsuario + "'" + " no es una contraseña válida.";

        mensajeAlerta.setText(mensaje);
        if (mensaje == "") mensajeAlerta.setVisible(false);
        else mensajeAlerta.setVisible(true);

        mensajeAlerta.setText(mensaje);
        System.out.println(mensaje);
        log.setMensajeLog(mensaje);
        log.generarLog("registro");
        mensaje = "Exito en el registro.";
        mensajeAlerta.setVisible(true);

        userAPIClient = new UserAPIClient();
        userAPIClient.register(emailUsuario, nombreUsuario, contraseniaUsuario, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
            }

            @Override
            public void onError(Object error) {
                System.out.println(((Error) error).getError());
            }
        });
    }
}