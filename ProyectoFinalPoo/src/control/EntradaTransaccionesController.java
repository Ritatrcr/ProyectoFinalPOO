/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class EntradaTransaccionesController implements Initializable {

    @FXML
    private Label btnRegresar;
    @FXML
    private TextField txtClave;
    @FXML
    private Button entrar;
    
     private GestionClientes gesCli;
     private ClienteController cliCont;
     private TransaccionesController transCon;
     
     private ArrayList<Cliente> clientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.gesCli = new GestionClientes();
        this.cliCont = new ClienteController();
        this.transCon = new TransaccionesController();
        // TODO
    }    

    @FXML
    private void doRegresar(MouseEvent event) 
    {
         try
        {
           FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Principal.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root); 
            
            Stage stage = new Stage();          
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Bienvenid@");
            stage.show();

            Stage myStage = (Stage)this.btnRegresar.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }

  
    @FXML
    private void doEntrar(MouseEvent event) 
    {
        this.traeClientes();
        
        boolean existe=false;
        for(Cliente cliente:this.clientes)
        {
            if (cliente.getClave().equals(this.txtClave.getText()))
            {
                try
                 {
                    FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Transacciones.fxml"));
                     Parent root=loader.load();
                     Scene scene=new Scene(root); 
                     
                      TransaccionesController controlVentana2=loader.getController();
                      controlVentana2.defineCliente(cliente);

                     Stage stage = new Stage();          
                     stage.setScene(scene);
                     stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
                     stage.setResizable(false);
                     stage.setTitle("Transacciones");
                     stage.show();

                     Stage myStage = (Stage)this.btnRegresar.getScene().getWindow();
                     myStage.close();
                     
                 }
                 catch (IOException ex)
                 {
         //           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
                 } 
                existe=true;
                break;
            }
            
        }
        if(!existe)
        {
            this.cliCont.manageMessages(1, "Clave Incorrecta");
            this.txtClave.setText(null);
           
        }
     
    }
    
    //========================================================
     private void traeClientes()
    {
        this.clientes=this.gesCli.getTodos();

    }
    
}
