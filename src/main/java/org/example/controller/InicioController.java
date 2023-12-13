package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import org.example.api.APICallback;
import org.example.api.ChatAPIClient;
import org.example.api.MessageAPIClient;
import org.example.api.UserAPIClient;
import org.example.listCell.MessageFXMLListCell;
import org.example.model.Chat;
import org.example.model.Message;
import org.example.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.App.userMain;

public class InicioController {

  @FXML
  private Label nombreChatId;
  @FXML
  private Label bienvenidoUsuario;
  private ChatAPIClient chatAPIClient;
  private UserAPIClient userAPIClient;
  private MessageAPIClient messageAPIClient;

  @FXML
  private ListView<User> listViewIdNuevo;
  @FXML
  private ListView<Chat> listViewIdHistorial;
  @FXML
  private ListView<Message> listViewIdMensajes;
  @FXML
  private Button botonEnviarId;
  @FXML
  private Button botonEditarPerfil;
  @FXML
  private Button botonBorrarChat;

  public static int idChat = 0;

  @FXML
  private TextField contenedorMensajeId;
  
  private ObservableList<User> usuariosDisponibles = FXCollections.observableArrayList();
  private ObservableList<Chat> chatsDisponibles = FXCollections.observableArrayList();
  private ObservableList<Message> mensajesDisponibles = FXCollections.observableArrayList();
  private Map<Integer, List<Message>> chatMessagesMap = new HashMap<>();

  @FXML
  public void initialize() throws IOException, InterruptedException {
    bienvenidoUsuario.setText("Bienvenido don " + userMain.getUsername());
    userAPIClient = new UserAPIClient();
    chatAPIClient = new ChatAPIClient();
    messageAPIClient = new MessageAPIClient();
    initializeBotonBorrarChat();
    initializeListViewNuevo();
    initializeListViewHistorial();
    initializeBotonEnviar();
    initializeUserList();
    initializeChatList();
    initializeBotonEditarPerfil();
  }

  private void initializeListViewNuevo() {
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
  }

  private void comprobarNombreChat(ObservableList<Message> mensajesDisponibles) {
    int idUsuario = listViewIdHistorial.getSelectionModel().getSelectedItem().getUser1_id();
    idChat = ((Chat) listViewIdHistorial.getSelectionModel().getSelectedItem()).getId();
    if (idUsuario == userMain.getId()) {
      nombreChatId.setText(listViewIdHistorial.getSelectionModel().getSelectedItem().getUser2_username());
    } else {
      nombreChatId.setText(listViewIdHistorial.getSelectionModel().getSelectedItem().getUser1_username());
    }
  }

  private void initializeListViewHistorial() {
    listViewIdHistorial.setOnMouseClicked(mouseEvent -> {
      ObservableList<Message> mensajesDisponibles = FXCollections.observableArrayList();
      comprobarNombreChat(mensajesDisponibles);
      mostrarMensajes();
    });
  }

  private void mostrarMensajes() {
      ObservableList<Message> mensajesDisponibles = FXCollections.observableArrayList();
      try {
      messageAPIClient.getMessagesFromChat(idChat, new APICallback() {
        @Override
        public void onSuccess(Object response) throws IOException {
          List<Message> messages = (List<Message>) response;
          // sort messages
          messages.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
          for (Message message : messages) {
            mensajesDisponibles.add(message);
            
          }
          //listViewIdMensajes.setItems(mensajesDisponibles);
          //PREPARAR SET.CELL.FACTORY PARA poder entrar en los metodos de cada item
          //Solucionar este problema
          //listViewIdMensajes.setItems(mensajesDisponibles);
          listViewIdMensajes.setCellFactory(param -> new MessageFXMLListCell());
          listViewIdMensajes.setItems(mensajesDisponibles);
          
          
          //listViewIdMensajes.setItems(mensajesDisponibles);

        }

        @Override
        public void onError(Object error) {

        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void initializeBotonBorrarChat() {
    botonBorrarChat.setOnMouseClicked(mouseEvent -> {
      
      try {
        chatAPIClient.cleanChat(idChat, new APICallback() {
          @Override
          public void onSuccess(Object response) throws IOException {
            System.out.println("Chat limpiado correctamente");
            initializeChatList();
          }

          @Override
          public void onError(Object error) {
            System.out.println("Error al limpiar el chat");
          }
          
        });
        chatAPIClient.deleteChat(idChat, new APICallback() {
          @Override
          public void onSuccess(Object response) throws IOException {
            System.out.println("Chat borrado correctamente");
            initializeChatList();
          }

          @Override
          public void onError(Object error) {
            
          }
        });
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }
  private void initializeBotonEditarPerfil() {
    botonEditarPerfil.setOnMouseClicked(mouseEvent -> {
      
      try {
        userAPIClient.updateUser(userMain.getId(),"elDandy",userMain.getPassword(),userMain.getEmail(),userMain.getPhotourl(),new APICallback() {

          @Override
          public void onSuccess(Object response) throws IOException {
            // TODO Auto-generated method stub
            System.out.println("Usuario actualizado correctamente");
            userMain = (User) response;
            bienvenidoUsuario.setText("Bienvenido don " + userMain.getUsername());
          }

          @Override
          public void onError(Object error) {
            System.out.println("Error al actualizar el usuario");
          }
          
        });
      } catch (Exception e) {
        System.out.println("Error al actualizar el usuario");

      }
    });
  }
  private void initializeBotonEnviar() {
    botonEnviarId.setOnMouseClicked(mouseEvent -> {
      String mensaje = contenedorMensajeId.getText();
      try {
        messageAPIClient.sendMessageToChat(idChat, mensaje, userMain.getId(), new APICallback() {
          @Override
          public void onSuccess(Object response) throws IOException {
            System.out.println("Mensaje enviado correctamente");
            contenedorMensajeId.setText("");
            mostrarMensajes();
          }

          @Override
          public void onError(Object error) {

          }
        });
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }
  private void initializeUserList() throws IOException, InterruptedException {
    userAPIClient.getAllUsers(new APICallback() {
      @Override
      public void onSuccess(Object response) throws IOException {
        List<User> users = (List<User>) response;
        for (User user : users) {
          usuariosDisponibles.add(user);
          System.out.println(user.getUsername());
        }
        // sort alphabetically the list
        // listChatNuevo.sort(String::compareTo);
        // items = FXCollections.observableArrayList(listChatNuevo);

        listViewIdNuevo.setItems(usuariosDisponibles);
      }

      @Override
      public void onError(Object error) {

      }
    });
  }

  private void initializeChatList() {
    chatsDisponibles = FXCollections.observableArrayList();
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