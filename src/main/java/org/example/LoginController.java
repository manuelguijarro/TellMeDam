package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    private void switchToInicio() throws IOException {
        App.setRoot("registro");
    }
    @FXML
    private void switchToRegistro() throws IOException {
        App.setRoot("registro");
    }
}
