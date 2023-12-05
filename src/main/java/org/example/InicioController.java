package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.example.model.Chat;

public class InicioController {

    @FXML
    private TextArea fileContentTextArea;
    private ChatAPIClient chatAPIClient;
    private UserAPIClient userAPIClient;
    private ObservableList<String> items;
    @FXML
    private         ListView<String> listViewId;

    @FXML
    public void initialize() {
      ArrayList<String> list = new ArrayList<>();

        chatAPIClient = new ChatAPIClient();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            chatAPIClient.getAllChatsFromUser(1, new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    List<Chat> chats = (List<Chat>) response;
                    for (Chat chat : chats) {
                      int idUser1 = chat.getUser1_id();
                      int idUser2 = chat.getUser2_id();
                      /*if(idUser1 ==){

                      }*/
                        list.add(chat.getUser1_username());
                    }
                    items = FXCollections.observableArrayList(list);
                    listViewId.setItems(items);
                    }
                @Override
                public void onError(Object error) {
                    System.out.println(error);
                }
                
                
            });
        });
        executorService.shutdown();
    }

}