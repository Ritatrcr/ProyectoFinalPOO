/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.File;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import negocio.Cliente;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class AllClientesController implements Initializable {

    @FXML
    private ComboBox<String> cbxGenero;
    @FXML
    private ComboBox<String> cbxProductos;
    
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colNombre;
    @FXML
    private TableColumn<?, ?> colGenero;
    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TableView<Cliente> tableView;
    
    
    private ArrayList<Cliente> clientes;
    private ObservableList<Cliente> obsClientes;
    private ObservableList<Cliente> filtrados;
    @FXML
    private ImageView imgFoto;
    @FXML
    private Button regeresar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        this.filtrados=FXCollections.observableArrayList();
        this.llenaCombos();
        this.modelaTabla();
    }    

    @FXML
    private void doBuscarGenero(ActionEvent event) 
    {
        int index;
        this.filtrados.clear();
        index=this.cbxGenero.getSelectionModel().getSelectedIndex();
        
        if(index==0)
            this.tableView.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    if(cliente.getGenero()==this.cbxGenero.getSelectionModel().getSelectedItem().charAt(0))
                        this.filtrados.add(cliente);
                }
            }
            this.tableView.setItems(this.filtrados);
        } 
    }

    @FXML
    private void doBuscarProducto(ActionEvent event) 
    {
        int index;
        this.filtrados.clear();
        index=this.cbxProductos.getSelectionModel().getSelectedIndex();
        
        if(index==0)
            this.tableView.setItems(this.obsClientes);
        else
        {
            if(index>0)
            {
                for(Cliente cliente:this.obsClientes)
                {
                    boolean productos[]=cliente.getProductos();
                    if(productos[index-1]==true)
                        this.filtrados.add(cliente);
                }
            }
            this.tableView.setItems(this.filtrados);
        } 
    }

    @FXML
    private void doBuscarNombre(ActionEvent event) {
        String filtroNombre;
        
        filtroNombre=this.txtFieldNombre.getText();
        if(filtroNombre.isEmpty())
            this.tableView.setItems(this.obsClientes);
        else
        {
            this.filtrados.clear();
            for(Cliente p:this.obsClientes)
            {
              if((p.getNombre().toLowerCase()).contains(filtroNombre.toLowerCase()))
                  this.filtrados.add(p);
            }
            this.tableView.setItems(this.filtrados);
        }
    }
    
    @FXML
    private void doPoneFoto(MouseEvent event)
    {
        String carre;
        
        Cliente perso=this.tableView.getSelectionModel().getSelectedItem();
        if(perso!=null)
        {
            File imgFile=new File("././Images/"+perso.getFoto());
            String url=imgFile.toURI().toString();
            Image image=new Image(url, true);
            this.imgFoto.setImage(image);
        }
    }
    
    @FXML
    private void doRegresar(ActionEvent event) 
    {
       try
        {
          FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Cliente.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root); 
            
            Stage stage = new Stage();          
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Manejo De Clientes");
            stage.show();

            //Lo de arriba es únicamente para crear la ventana ----------------------------------------

            Stage myStage = (Stage)this.regeresar.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }
    
    

//=============================================================
    public void setClientes(ArrayList<Cliente>clientes)
    {
        this.clientes=clientes;
        this.obsClientes=FXCollections.observableArrayList(this.clientes);
        this.tableView.setItems(this.obsClientes);
    }
    
    private void llenaCombos()
    {
        
        this.cbxGenero.getItems().add("Todos");
        this.cbxGenero.getItems().add("Hombre");
        this.cbxGenero.getItems().add("Mujer");
        this.cbxGenero.getItems().add("Otro");
        
        
        this.cbxProductos.getItems().add("Todos");
        this.cbxProductos.getItems().add("Cuenta Ahorros");
        this.cbxProductos.getItems().add("Cuenta Corriente");
        this.cbxProductos.getItems().add("CDT");
        this.cbxProductos.getItems().add("Tarjeta Visa");
        this.cbxProductos.getItems().add("Tarjeta América");

    }
    private void modelaTabla()
    {
        this.colId.setCellValueFactory(new PropertyValueFactory("Id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("Nombre"));
        this.colGenero.setCellValueFactory(new PropertyValueFactory("Género"));
        
    }

    

    
    
   

    
}