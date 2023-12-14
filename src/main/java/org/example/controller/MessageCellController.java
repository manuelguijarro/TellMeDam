package org.example.controller;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import static org.example.App.userMain;


public class MessageCellController {
    @FXML
    private Label nombreChatId;
 


    public void mostrarChat(String mensajeChat, int idSender) {


        // Cambiar el color del texto de nombreChatId a azul
        // (Asegúrate de que el estilo no haga que el texto sea invisible o del mismo color que el fondo)

        // Mostrar el mensaje en el Label
        nombreChatId.setText(mensajeChat);







    }
}

