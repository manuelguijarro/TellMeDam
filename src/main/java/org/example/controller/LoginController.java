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
    private String leerLog(String filePath) throws IOException {
        String file = "src/main/java/org/example/logs/login.log";
        String line;
        String log = "";
        try{
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            while ((line = bfr.readLine()) != null) {
                log += line + "\n";
            }
            bfr.close();
            return log;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;

    }
    private void logearseLog(String mensaje) throws IOException, InterruptedException {
        // ...

        // Generate a function and save the mensaje string in a file called registro.log
        String filePath = "src/main/java/org/example/logs/login.log";
        String log = leerLog(filePath);
        FileWriter writer = new FileWriter(filePath);
        
        writer.append(log+ mensaje + "\n");
        
        
        writer.close();
        
        // ...
    }
    @FXML
    private void switchToInicio() throws IOException, InterruptedException {

        
        String emailUsuarioInput = emailUsuario.getText();
        
        String contraseniaUsarioInput = contraseniaUsario.getText();
        userAPIClient = new UserAPIClient();
        userAPIClient.login(emailUsuarioInput, contraseniaUsarioInput, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
                userMain = (User) response;
                System.out.println("Exito en el login");
                //Enviar a inicioPagina
                App.setRoot("inicio");
            }

            @Override
            public void onError(Object error) {
              Error e = (Error) error;
              Date date = new Date();
              String formattedDate = date.toString();
              String mensajeError =formattedDate + ": " + "Failed registration["+e.getError()+"]";
              
                System.out.println(mensajeError);
                try {
                  logearseLog(mensajeError);
                } catch (Exception e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
                }
            }
        });

    }
    @FXML
    private void switchToRegistro() throws IOException {
        App.setRoot("registro");
    }
}
