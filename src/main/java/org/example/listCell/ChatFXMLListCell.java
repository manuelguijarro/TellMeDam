package org.example.listCell;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.example.controller.ChatCellController;
import org.example.model.Chat;

public class ChatFXMLListCell extends ListCell<Chat> {

  @Override
  public void updateItem(Chat chat, boolean empty) {
    super.updateItem(chat, empty);
    if (empty || chat == null) {
      setText(null);
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("chatFXMLListCell.fxml"));
        Parent root = loader.load();
        ChatCellController chatCellController = loader.getController();
        chatCellController.setChat(chat.getUser1_id(),chat.getUser1_username(),chat.getUser2_id(),chat.getUser2_username());
        setGraphic(root);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
