package org.example;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class MessageCellController {
    @FXML
    private Label nombreChatId;
 


    public void mostrarChat(String mensajeChat) {
        nombreChatId.setText(mensajeChat);
    }
}

