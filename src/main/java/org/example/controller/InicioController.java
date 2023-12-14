package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.api.APICallback;
import org.example.api.ChatAPIClient;
import org.example.api.MessageAPIClient;
import org.example.api.UserAPIClient;
import org.example.listCell.ChatFXMLListCell;
import org.example.listCell.MessageFXMLListCell;
import org.example.model.Chat;
import org.example.model.Message;
import org.example.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class InicioController {

    @FXML
    private Button botonCerrarSesion;
    @FXML
    private Label nombreChatId;
    @FXML
    private Label bienvenidoUsuario;
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
    @FXML
    private TextField contenedorMensajeId;

    private UserAPIClient userAPIClient;
    private ChatAPIClient chatAPIClient;
    private MessageAPIClient messageAPIClient;
  private ObservableList<Message> mensajesDisponibles = FXCollections.observableArrayList();
    private ObservableList<User> usuariosDisponibles = FXCollections.observableArrayList();
    private ObservableList<Chat> chatsDisponibles = FXCollections.observableArrayList();

    private int idChat = 0;

    @FXML
    public void initialize() throws IOException, InterruptedException {
        bienvenidoUsuario.setText("Bienvenido don " + App.userMain.getUsername());
        userAPIClient = new UserAPIClient();
        chatAPIClient = new ChatAPIClient();
        messageAPIClient = new MessageAPIClient();
        initializeComponents();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("Ejecutando carga de chats cada 2 segundos");
                try {
                    mostrarMensajesAPI();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 2000); // 0 indica el retraso inicial, y 2000 indica el intervalo en milisegundos (2 segundos)

    }

    private void initializeComponents() {
        initializeMostrarMensajes();
        initializeBotonBorrarChat();
        initializeCrearNuevoChat();
        initializeBotonEnviar();
        initializeListaUsuario();
        initializeListaChat();
        initializeBotonEditarPerfil();
        initializebotonCerrarSesion();
    }

    private void initializeBotonBorrarChat() {
        botonBorrarChat.setOnMouseClicked(this::borrarChat);
    }

    private void borrarChat(MouseEvent mouseEvent) {
        try {
            limpiarChatAPI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void limpiarChatAPI() {
        chatAPIClient.cleanChat(idChat, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println("Chat limpiado correctamente");
                eliminarChatAPI();
            }

            @Override
            public void onError(Object error) {
                System.out.println("Error al limpiar el chat");
            }
        });
    }

    private void eliminarChatAPI() {
        chatAPIClient.deleteChat(idChat, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println("Chat borrado correctamente");
                initializeListaChat();
            }

            @Override
            public void onError(Object error) {
                // Handle error if needed
            }
        });
    }

    private void initializeMostrarMensajes() {
        listViewIdHistorial.setOnMouseClicked(mouseEvent -> {
            ObservableList<Message> mensajesDisponibles = FXCollections.observableArrayList();
            comprobarNombreChat(mensajesDisponibles);
            mostrarMensajes();
        });
    }

    private void mostrarMensajes() {
        try {
            mostrarMensajesAPI();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarListViewMensajes(List<Message> messages) {
        messages.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        mensajesDisponibles.setAll(messages);
        listViewIdMensajes.setItems(mensajesDisponibles);
        listViewIdMensajes.setCellFactory(param -> new MessageFXMLListCell());
    }

    private void mostrarMensajesAPI() throws IOException {
        if (idChat == 0) return;
        messageAPIClient.getMessagesFromChat(idChat, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
              //function setInterval 2segs.
                
              List<Message> messages = (List<Message>) response;
                cargarListViewMensajes(messages);
            }

            @Override
            public void onError(Object error) {
                // Handle the error according to your needs
            }
        });
    }

    private void comprobarNombreChat(ObservableList<Message> mensajesDisponibles) {
        Chat selectedChat = listViewIdHistorial.getSelectionModel().getSelectedItem();
        int idUsuario = selectedChat.getUser1_id();
        idChat = selectedChat.getId();
        String usernameToShow = (idUsuario == App.userMain.getId()) ? selectedChat.getUser2_username() : selectedChat.getUser1_username();
        nombreChatId.setText(usernameToShow);
    }

    private void initializebotonCerrarSesion() {
        botonCerrarSesion.setOnMouseClicked(mouseEvent -> {
            try {
                cambiarInicio();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void cambiarInicio() throws IOException {
        App.userMain = null;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        App.setRoot("login");
    }

    private void initializeBotonEditarPerfil() {
        botonEditarPerfil.setOnMouseClicked(mouseEvent -> {
            try {
                actualizarUsuarioAPI();
            } catch (Exception e) {
                System.out.println("Error al actualizar el usuario");
            }
        });
    }

    private void actualizarUsuarioAPI() {
        try {
            userAPIClient.updateUser(App.userMain.getId(), "elDandy", App.userMain.getPassword(), App.userMain.getEmail(), App.userMain.getPhotourl(), new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    System.out.println("Usuario actualizado correctamente");
                    App.userMain = (User) response;
                    bienvenidoUsuario.setText("Bienvenido don " + App.userMain.getUsername());
                }

                @Override
                public void onError(Object error) {
                    System.out.println("Error al actualizar el usuario");
                }
            });
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeBotonEnviar() {
        botonEnviarId.setOnMouseClicked(mouseEvent -> {
            try {
                enviarMensajeAPI();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void enviarMensajeAPI() throws IOException {
        String mensaje = contenedorMensajeId.getText();
        messageAPIClient.sendMessageToChat(idChat, mensaje, App.userMain.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println("Mensaje enviado correctamente");
                contenedorMensajeId.setText("");
                mostrarMensajes();
            }

            @Override
            public void onError(Object error) {
                // Handle error if needed
            }
        });
    }

    private void initializeCrearNuevoChat() {
        listViewIdNuevo.setOnMouseClicked(mouseEvent -> {
            try {
                crearChatAPI();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void crearChatAPI() {
        User selectedUser = listViewIdNuevo.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            int idUser = selectedUser.getId();
            chatAPIClient.createChat(App.userMain.getId(), idUser, new APICallback() {
                @Override
                public void onSuccess(Object response) throws IOException {
                    System.out.println(response);
                    obtenerChatAPI();
                }

                @Override
                public void onError(Object error) {
                    // Handle error if needed
                }
            });
        }
    }

    private void initializeListaUsuario() {
        try {
            obtenerUsuarioAPI();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void obtenerUsuarioAPI() throws IOException, InterruptedException {
        userAPIClient.getAllUsers(new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<User> users = (List<User>) response;
                usuariosDisponibles.setAll(users);
                listViewIdNuevo.setItems(usuariosDisponibles);
            }

            @Override
            public void onError(Object error) {
                // Handle error if needed
            }
        });
    }

    private void initializeListaChat() {
        obtenerChatAPI();
    }

    private void obtenerChatAPI() {
        chatAPIClient.getAllChatsFromUser(App.userMain.getId(), new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                List<Chat> chats = (List<Chat>) response;
                chatsDisponibles.setAll(chats);
                listViewIdHistorial.setItems(chatsDisponibles);

              listViewIdHistorial.setCellFactory(param -> new ChatFXMLListCell());
            }

            @Override
            public void onError(Object error) {
                System.out.println(error);
            }
        });
    }
}
