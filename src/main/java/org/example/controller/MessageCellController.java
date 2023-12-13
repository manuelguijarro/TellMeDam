package org.example.controller;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import static org.example.App.userMain;


public class MessageCellController {
    @FXML
    private Label nombreChatId;
 


    public void mostrarChat(String mensajeChat, int idSender) {


          // change color text nombreChatid to blue
            nombreChatId.setText(mensajeChat);







    }
}

