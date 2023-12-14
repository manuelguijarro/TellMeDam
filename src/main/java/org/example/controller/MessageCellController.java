package org.example.controller;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import static org.example.App.userMain;
import static org.example.App.estado;


public class MessageCellController {
    @FXML
    private Label nombreChatId;
    private boolean cambiarNombre;


    public void mostrarChat(String mensajeChat, int idSender) {
      if (idSender == userMain.getId()) {
          nombreChatId.setStyle("-fx-text-fill: blue;");
          nombreChatId.setText("<" + userMain.getUsername() + ">");
      } else {
          nombreChatId.setStyle("-fx-text-fill: red;");
          nombreChatId.setText("otro usuario");
      }
      nombreChatId.setText(mensajeChat);

        
        // Cambiar el color del texto de nombreChatId a azul
        // (Asegúrate de que el estilo no haga que el texto sea invisible o del mismo color que el fondo)

        // Mostrar el mensaje en el Label
        

      }





    }


