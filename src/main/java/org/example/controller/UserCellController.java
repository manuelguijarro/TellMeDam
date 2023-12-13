package org.example.controller;


import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserCellController {
    @FXML
    private Label nombreUsuarioId;
    @FXML
    private ImageView imagenUsuarioId;


    public void setDatosUsuario(String imgUsuario, String nombreUsuario) {
      Image image = new Image(imgUsuario);
      imagenUsuarioId = new ImageView();
      imagenUsuarioId.setImage(image);
      imagenUsuarioId.setFitHeight(100);
      imagenUsuarioId.setFitWidth(200);
      imagenUsuarioId.setVisible(true);
      nombreUsuarioId.setText(nombreUsuario);
    }
}
