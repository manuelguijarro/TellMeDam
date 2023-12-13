package org.example.listCell;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import org.example.controller.MessageCellController;
import org.example.model.Message;

public class MessageFXMLListCell extends ListCell<Message> {
  private MessageCellController messageCellController;

  @Override
  public void updateItem(Message message, boolean empty) {
    //export message
    if (message != null) {
        String exportedMessage = message.getContent();
        // TODO: Export the message to a file or perform any other necessary action
        System.out.println("Exported message: " + exportedMessage);
    }
    super.updateItem(message, empty);
    if (empty || message == null) {
      setText(null);
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("messageFXMLListCell.fxml"));
        Parent root = loader.load();
        messageCellController = loader.getController();
        messageCellController.mostrarChat(message.getContent(), message.getIdsender());
        setGraphic(root);
      } catch (Exception e) {
        
      }
    }
  }
}