package org.example.listCell;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.controller.UserCellController;
import org.example.model.User;

public class UserFXMLListCell extends ListCell<User> {

  @Override
  public void updateItem(User user, boolean empty) {
    super.updateItem(user, empty);
    if (empty || user == null) {
      setText(null);
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userFXMLListCell.fxml"));
        Parent root = loader.load();
        UserCellController userCellController = loader.getController();
        userCellController.setDatosUsuario(user.getPhotourl(), user.getUsername());
        setGraphic(root);
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
  }
}
