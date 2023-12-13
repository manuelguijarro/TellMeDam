package org.example.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.api.APICallback;
import org.example.App;
import org.example.api.UserAPIClient;
import org.example.model.Error;
public class RegistroController {
    @FXML
    private Label mensajeAlerta;
    @FXML
    private TextField nombreUsuarioTextField;
    @FXML
    private TextField emailUsuarioTextField;
    @FXML
    private TextField contraseniaUsuarioTextField;
    private UserAPIClient userAPIClient;
    //generate inizalizer function
    @FXML
    public void initialize() {
        mensajeAlerta.setVisible(false);
      
    }
    private String leerLog(String filePath) throws IOException {
        String file = "src/main/java/org/example/logs/registro.log";
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
    private void registrarseLog(String mensaje) throws IOException, InterruptedException {
        // ...

        // Generate a function and save the mensaje string in a file called registro.log
        String filePath = "src/main/java/org/example/logs/registro.log";
        String log = leerLog(filePath);
        FileWriter writer = new FileWriter(filePath);
        Date date = new Date();
        String formattedDate = date.toString();
        writer.append(log+formattedDate + ": " + mensaje + "\n");
        
        
        writer.close();
        
        // ...
    }
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }@FXML
    private void registrarse() throws IOException, InterruptedException {
        String mensaje = "";
        String nombreUsuario = nombreUsuarioTextField.getText();
        String emailUsuario = emailUsuarioTextField.getText();
        String contraseniaUsuario = contraseniaUsuarioTextField.getText();
        if(emailUsuario.isEmpty() || contraseniaUsuario.isEmpty()){
            mensaje = "Failed registration[campos vacios]: email o contraseña no pueden estar vacíos.";
            mensajeAlerta.setText(mensaje);
            mensajeAlerta.setVisible(true);
            System.out.println(mensaje);
            registrarseLog(mensaje);
            return;
        }
        if (!emailUsuario.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
          mensaje = "Failed registration[email]: '" + emailUsuario+"'" + " no es un email válido.";
          mensajeAlerta.setText(mensaje);
          mensajeAlerta.setVisible(true);
          System.out.println(mensaje);
          registrarseLog(mensaje);
          return;
      }
      if (!contraseniaUsuario.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
        mensaje = "Failed registration[contraseña]: '"+contraseniaUsuario+"'" + " no es una contraseña válida.";
        mensajeAlerta.setText(mensaje);
        mensajeAlerta.setVisible(true);  
        System.out.println(mensaje);
        registrarseLog(mensaje);
          return;
      }
      mensaje = "Exito en el registro.";
      mensajeAlerta.setVisible(true);
        //generate a function and save the mensaje string in a file call registro.log and save it in a folder called logs in the project root folder.

        userAPIClient = new UserAPIClient();
        userAPIClient.register(emailUsuario, nombreUsuario, contraseniaUsuario, new APICallback() {
            @Override
            public void onSuccess(Object response) throws IOException {
                System.out.println(response);
            }

            @Override
            public void onError(Object error) {
                System.out.println(((Error) error).getError() );
            }
        });
    }
}