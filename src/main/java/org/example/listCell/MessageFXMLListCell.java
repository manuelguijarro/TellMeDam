package org.example.listCell;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import org.example.controller.MessageCellController;
import org.example.model.Message;

public class MessageFXMLListCell extends ListCell<Message> {

  @Override
  protected void updateItem(Message message, boolean empty) {
    super.updateItem(message, empty);

    
    if (empty || message == null) {
      setText(null);
      setGraphic(null);
    } else {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/view/messageFXMLListCell.fxml"));
Parent root = loader.load();
        MessageCellController controller = loader.getController();
        controller.mostrarChat(message.getContent(), message.getIdsender());
        setGraphic(root);
        
      } catch (Exception e) {
        System.out.println("Error al cargar graficos");
        e.printStackTrace();
        setGraphic(null); // Establecer como nulo en caso de error
      }
    }
  }
}
