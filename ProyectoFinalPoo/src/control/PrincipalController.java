/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class PrincipalController implements Initializable {

    @FXML
    private Button btnGestionClientes;
    @FXML
    private Button btnGestionProductos;
    @FXML
    private Button btnTransacciones;
    @FXML
    private Button btnSalir;
    @FXML
    private ImageView imageView;

    /**
     * Initializes the controller class.
     */
    
    
    //verificacion campos de int
    //verificacion claves
    //cambio tipo producto a int
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
        Image image = new Image("file:" +"././ImagesInterface/OINK.jpeg");
        imageView.setImage(image);
    }    

    @FXML
    private void doGestionClientes(ActionEvent event) {
    
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Cliente.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root); 
            Stage stage = new Stage();          
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Gestión Clientes");
            stage.show();

            Stage myStage = (Stage)this.btnGestionClientes.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 

    }

    @FXML
    private void doGestionProductos(ActionEvent event) 
    {
        try
        {
          FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Productos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root); 
            
            
            Stage stage = new Stage();          
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Gestión Productos");
            stage.show();

            Stage myStage = (Stage)this.btnGestionProductos.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }

    @FXML
    private void doTransacciones(ActionEvent event) 
    {
         try
        {
          FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/EntradaTransacciones.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root); 
            
            Stage stage = new Stage();          
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Transacciones");
            stage.show();

            Stage myStage = (Stage)this.btnTransacciones.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }

    @FXML
    private void doSalir(ActionEvent event) 
    {
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

   
    
}
