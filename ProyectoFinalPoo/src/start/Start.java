/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package start;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
    
    public static void main (String[] args ){
        launch(args);
    }
    
    @Override
    public void start(Stage ventana) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Principal.fxml"));
        Scene scene = new Scene (root);
        ventana.setScene(scene);
        ventana.setTitle("Bienvenid@");
        ventana.setResizable(false);
//      ventana.setOnCloseRequest(event -> {event.consume();}); //anular X de salida
        ventana.show();
    }
}
