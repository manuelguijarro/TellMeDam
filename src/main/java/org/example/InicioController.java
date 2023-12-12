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

import org.example.api.ChatAPIClient;
import org.example.model.Chat;
import org.example.model.User;

import static org.example.App.userMain;

public class InicioController {

    @FXML
    private TextArea fileContentTextArea;
    private ChatAPIClient chatAPIClient;
    private UserAPIClient userAPIClient;
    private MessageAPIClient messageAPIClient;
    private ObservableList<String> items;
    @FXML
    private ListView<String> listViewIdNuevo;
    @FXML
    private ListView<String> listViewIdHistorial;

    @FXML
    public void initialize() throws IOException, InterruptedException {
      ObservableList<User> usuariosDisponibles = FXCollections.observableArrayList();
      //Crear un nuevo listView para los chat si tienen imagen, sino dejarlo asi
      ObservableList<User> usuariosChatAbierto = FXCollections.observableArrayList();

listView.setItems(personas);
        ArrayList<String> listHistorial = new ArrayList<>();
        ArrayList<String> listChatNuevo= new ArrayList<>();
        userAPIClient = new UserAPIClient();
        chatAPIClient = new ChatAPIClient();
        messageAPIClient = new MessageAPIClient();
        //fix the next line: String idUser = userMain.getId().toString();
        
       /* chatAPIClient.createChat(userMain.getId(), 1, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
            }

            @Override
            public void onError(Object error) {

            }

        });
        messageAPIClient.sendMessage(userMain.getId(), 1, "hola", new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
            }

            @Override
            public void onError(Object error) {

            }
        });*/
            userAPIClient.getAllUsers(new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    List<User> users = (List<User>) response;
                    for (User user : users) {
                        usuariosDisponibles.add(user);
                        System.out.println(user.getUsername());
                    }
                    //sort alphabetically the list
                    //listChatNuevo.sort(String::compareTo);
                   // items = FXCollections.observableArrayList(listChatNuevo);
                    
                    listViewIdNuevo.setItems(usuariosDisponibles);
                }

                @Override
                public void onError(Object error) {

                }
            });
            chatAPIClient.getAllChatsFromUser(userMain.getId(), new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    List<Chat> chats = (List<Chat>) response;
                    for (Chat chat : chats) {
                      int idUser1 = chat.getUser1_id();
                      int idUser2 = chat.getUser2_id();
                      if(idUser1 ==userMain.getId()){
                          listHistorial.add(chat.getUser2_username());
                      }else{
                          listHistorial.add(chat.getUser2_username());
                      }
                    }
                    items = FXCollections.observableArrayList(listHistorial);
                    listViewIdHistorial.setItems(items);
                    }
                @Override
                public void onError(Object error) {
                    System.out.println(error);
                }
                
                
            });

    }

}