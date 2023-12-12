package org.example;

import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import javafx.scene.control.TextField;
import org.example.UserAPIClient;
import org.example.ChatAPIClient;
import org.example.model.Chat;
import org.example.model.Message;
import org.example.model.User;
import org.w3c.dom.events.MouseEvent;

import static org.example.App.userMain;


public class InicioController {

    @FXML
    private TextArea fileContentTextArea;
    private ChatAPIClient chatAPIClient;
    private org.example.UserAPIClient userAPIClient;
    private org.example.MessageAPIClient messageAPIClient;
    private ObservableList<String> items;
    @FXML
    private ListView<User> listViewIdNuevo;
    @FXML
    private ListView<Chat> listViewIdHistorial;
    @FXML 
    private ListView<Message> listViewIdMensajes;
    @FXML
    private Button botonEnviarId;
    public static int idChat = 0;
    @FXML
    private TextField contenedorMensajeId;
    
    @FXML
    public void initialize() throws IOException, InterruptedException {
        ObservableList<User> usuariosDisponibles = FXCollections.observableArrayList();
        ObservableList<Chat> chatsDisponibles = FXCollections.observableArrayList();

        //Crear un nuevo listView para los chat si tienen imagen, sino dejarlo asi
        ObservableList<User> usuariosChatAbierto = FXCollections.observableArrayList();

        ArrayList<Message> listHistorial = new ArrayList<>();
        ArrayList<String> listChatNuevo= new ArrayList<>();
        userAPIClient = new UserAPIClient();
        chatAPIClient = new ChatAPIClient();
        messageAPIClient = new MessageAPIClient();


        listViewIdNuevo.setOnMouseClicked(mouseEvent -> {
            int idUser = ((User) listViewIdNuevo.getSelectionModel().getSelectedItem()).getId();
            chatAPIClient.createChat(userMain.getId(), idUser, new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    System.out.println(response);

                }

                @Override
                public void onError(Object error) {

                }
            });
        });
        listViewIdHistorial.setOnMouseClicked(mouseEvent -> {
            ObservableList<Message>mensajesDisponibles = FXCollections.observableArrayList();
             idChat = ((Chat) listViewIdHistorial.getSelectionModel().getSelectedItem()).getId();
            try {
                messageAPIClient.getMessagesFromChat(idChat, new APICallback() {
                    @Override
                    public void onSuccess(Object response) throws IOException {
                        List<Message> messages = (List<Message>) response;
                        for (Message message : messages) {
                            mensajesDisponibles.add(message);
                        }
                        listViewIdMensajes.setItems(mensajesDisponibles);
                    }

                    @Override
                    public void onError(Object error) {

                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
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
        botonEnviarId.setOnMouseClicked(mouseEvent -> {
            String mensaje = contenedorMensajeId.getText();
            try {
                messageAPIClient.sendMessageToChat(idChat, mensaje, userMain.getId(), new APICallback() {

                    @Override
                    public void onSuccess(Object response) throws IOException {
                        System.out.println("okey mensaje enviado");
                    }

                    @Override
                    public void onError(Object error) {

                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        chatAPIClient.getAllChatsFromUser(userMain.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> chats = (List<Chat>) response;
                for (Chat chat : chats) {
                    chatsDisponibles.add(chat);
                }
                listViewIdHistorial.setItems(chatsDisponibles);
            }
            @Override
            public void onError(Object error) {
                System.out.println(error);
            }


        });

    }


}