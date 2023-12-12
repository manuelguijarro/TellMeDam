package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UserCellController {
    @FXML
    private Label nombreUsuarioId;
    @FXML
    private ImageView imagenUsuarioId;

    public void setDatosUsuario(String imgUsuario, String nombreUsuario) {
        imagenUsuarioId.setImage(new javafx.scene.image.Image(imgUsuario));
        nombreUsuarioId.setText(nombreUsuario);
    }
}

