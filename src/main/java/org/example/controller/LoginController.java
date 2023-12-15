package org.example.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.api.APICallback;
import org.example.App;
import org.example.api.UserAPIClient;
import org.example.model.Log;
import org.example.model.User;
import org.example.model.Error;

import static org.example.App.userMain;

public class LoginController {
  @FXML
  private Label tituloApp;
  @FXML
  private TextField emailUsuario;
  @FXML
  private TextField contraseniaUsario;
  @FXML
  private UserAPIClient userAPIClient;


  @FXML
  private void switchToInicio() throws IOException, InterruptedException {
    String emailUsuarioInput = emailUsuario.getText();
    String contraseniaUsarioInput = contraseniaUsario.getText();
    userAPIClient = new UserAPIClient();
    iniciarSesionAPI(emailUsuarioInput,contraseniaUsarioInput);
  }
  private void iniciarSesionAPI(String emailUsuarioInput, String contraseniaUsarioInput) throws IOException, InterruptedException {
    userAPIClient.login(emailUsuarioInput, contraseniaUsarioInput, new APICallback() {
      @Override
      public void onSuccess(Object response) throws IOException {
        System.out.println(response);
        userMain = (User) response;
        System.out.println("Exito en el login");
        // Enviar a inicioPagina

        App.setRoot("inicio");
      }
      
      @Override
      public void onError(Object error) {
        
        Error e = (Error) error;
        devolverMensajeError(e);

        
      }
    });
  }
private void devolverMensajeError(Error e) {
        

        String mensajeError = "Failed registration[" + e.getError() + "]";
        System.out.println(mensajeError);
        try {
          Log log = new Log(mensajeError);
          log.generarLog("login");
        } catch (Exception e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
  @FXML
  private void switchToRegistro() throws IOException {
    App.setRoot("registro");
  }
}
