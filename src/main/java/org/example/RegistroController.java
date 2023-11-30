package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistroController {
    @FXML
    private TextField nombreUsuarioTextField;
    @FXML
    private TextField emailUsuarioTextField;
    @FXML
    private TextField contraseniaUsuarioTextField;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }@FXML
    private void registrarse() throws IOException {
        String nombreUsuario = nombreUsuarioTextField.getText();
        String emailUsuario = emailUsuarioTextField.getText();
        String contraseniaUsuario = contraseniaUsuarioTextField.getText();

    }
}