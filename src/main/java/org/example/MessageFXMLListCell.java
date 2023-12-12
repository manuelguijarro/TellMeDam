package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.model.Message;

public class MessageFXMLListCell extends ListCell<Message> {

  @Override
  public void updateItem(Message message, boolean empty) {
    super.updateItem(message, empty);
    if (empty || message == null) {
      setText(null);
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("messageFXMLListCell.fxml"));
        Parent root = loader.load();
        MessageCellController messageCellController = loader.getController();
        messageCellController.mostrarChat(message.getContent());
        setGraphic(root);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
