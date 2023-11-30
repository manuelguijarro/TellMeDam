package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.model.Error;
public class RegistroController {
    @FXML
    private TextField nombreUsuarioTextField;
    @FXML
    private TextField emailUsuarioTextField;
    @FXML
    private TextField contraseniaUsuarioTextField;
    private UserAPIClient userAPIClient;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }@FXML
    private void registrarse() throws IOException, InterruptedException {
        
        String nombreUsuario = nombreUsuarioTextField.getText();
        String emailUsuario = emailUsuarioTextField.getText();
        String contraseniaUsuario = contraseniaUsuarioTextField.getText();
        userAPIClient = new UserAPIClient();
        userAPIClient.register(emailUsuario, nombreUsuario, contraseniaUsuario, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
            }

            @Override
            public void onError(Object error) {
                System.out.println(((Error) error).getError() );
            }
        });
    }
}