package org.example;

import java.io.IOException;
import javafx.fxml.FXML;

public class RegistroController {

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
}