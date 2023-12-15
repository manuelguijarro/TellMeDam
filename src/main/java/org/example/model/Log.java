package org.example.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
  private String mensajeLog;

  public Log(String mensajeLog) {
    Date date = new Date();
    String formattedDate = date.toString();
    this.mensajeLog = formattedDate + ": " + mensajeLog;

  }


  private String leerLog(String filePath) throws IOException {
    String file = filePath;
    String line;
    String log = "";
    try {
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
  public void generarLog(String ruta) throws IOException, InterruptedException {
    String filePath = "src/main/java/org/example/logs/"+ruta+".log";
    String log = leerLog(filePath);
    FileWriter writer = new FileWriter(filePath);
    writer.append(log + this.mensajeLog + "\n");
    writer.close();
  }


  public String getMensajeLog() {
    return mensajeLog;
  }


  public void setMensajeLog(String mensajeLog) {
    Date date = new Date();
    String formattedDate = date.toString();
    this.mensajeLog = formattedDate + ": " + mensajeLog;
  }
  
}
