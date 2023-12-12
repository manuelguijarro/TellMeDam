package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.model.User;

import static org.example.App.userMain;

public class LoginController {
    @FXML
    private TextField emailUsuario;
    @FXML
    private TextField contraseniaUsario;
    private UserAPIClient userAPIClient;
    @FXML
    private void switchToInicio() throws IOException, InterruptedException {
        String emailUsuarioInput = emailUsuario.getText();
        String contraseniaUsarioInput = contraseniaUsario.getText();
        userAPIClient = new UserAPIClient();
        userAPIClient.login(emailUsuarioInput, contraseniaUsarioInput, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
                userMain = (User) response;
                System.out.println("Exito en el login");
                //Enviar a inicioPagina
                App.setRoot("inicio");
            }

            @Override
            public void onError(Object error) {
                System.out.println(error);
            }
        });

    }
    @FXML
    private void switchToRegistro() throws IOException {
        App.setRoot("registro");
    }
}
