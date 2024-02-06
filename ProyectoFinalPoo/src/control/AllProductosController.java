/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.IOException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class AllProductosController implements Initializable {

    @FXML
    private ComboBox<String> cbxIdCliente;
    @FXML
    private ComboBox<String> cbxProductos;
    @FXML
    private TableView<Producto> tableView;
    @FXML
    private TableColumn<?, ?> colIdProducto;
    @FXML
    private TableColumn<?, ?> colIdCliente;
    @FXML
    private TableColumn<?, ?> colProducto;
    @FXML
    private TableColumn<?, ?> colApertura;
    @FXML
    private TableColumn<?, ?> colDisponibilidad;
    @FXML
    private TextField txtFieldId;
    @FXML
    private Label regresar;
    
    private ArrayList<Producto> productos;
    private ObservableList<Producto> obsProductos;
    private ObservableList<Producto> filtrados;
    private StringBuilder stringBuilderIdProducto;
    
    private ProductosController1 proCon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.filtrados = FXCollections.observableArrayList();
        this.obsProductos = FXCollections.observableArrayList();
        this.modelaTabla();
        this.proCon=new ProductosController1();
        
        // TODO
    }    

    
    @FXML
    private void doBuscarId(KeyEvent e) {
        String filtroIdProducto;
        
        filtroIdProducto = this.txtFieldId.getText();
        
        if(filtroIdProducto.isEmpty()){
            this.tableView.setItems(this.obsProductos);
        }
        else
        {
            this.filtrados.clear();
            for(Producto libro: this.productos){
                if((libro.getIdProducto().toLowerCase()).contains(filtroIdProducto.toLowerCase()))
                    this.filtrados.add(libro);
            }
            this.tableView.setItems(this.filtrados);
        }  
    }
    
    @FXML
    private void filtroId(ActionEvent event) 
    {
        int index;
        this.filtrados.clear();
        index = this.cbxIdCliente.getSelectionModel().getSelectedIndex();
        if(index == 0)
            this.tableView.setItems(this.obsProductos);
        else
        {
            for(Producto producto: this.obsProductos)
            {
                if(producto.getIdCliente().equals(this.cbxIdCliente.getSelectionModel().getSelectedItem()))
                    this.filtrados.add(producto);
            }
            this.tableView.setItems(this.filtrados);
        }
    }
    @FXML
    private void filtroProducto(ActionEvent event) 
    {
        int index;
        this.filtrados.clear();
        index = this.cbxProductos.getSelectionModel().getSelectedIndex();
        if(index == 0)
            this.tableView.setItems(this.obsProductos);
        else
        {
            for(Producto producto: this.obsProductos)
            {
                if(producto.getProducto().equals(this.cbxProductos.getSelectionModel().getSelectedItem()))
                    this.filtrados.add(producto);
            }
            this.tableView.setItems(this.filtrados);
        }
    }


    @FXML
    private void doRegresar(MouseEvent event)
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
            stage.setTitle("Gestión De Productos");
            stage.show();

            Stage myStage = (Stage)this.regresar.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    
    //=================================================================================================
    public void setProductos(ArrayList<Producto>productos)
    {
        this.productos=productos;
        this.obsProductos=FXCollections.observableArrayList(this.productos);
        this.tableView.setItems(this.obsProductos);
        this.llenaCombos(this.productos);
    }

     private void llenaCombos(ArrayList<Producto>productos)
    {
        this.cbxIdCliente.getItems().add("Todos");
        for(Producto producto: this.productos)
        {
            String idCliente = producto.getIdCliente();
            if (!this.cbxIdCliente.getItems().contains(idCliente)) 
            {
                this.cbxIdCliente.getItems().add(idCliente);
            }
        } 
        
        //productos
        this.cbxProductos.getItems().add("Todos");
        this.cbxProductos.getItems().add("Cuenta Ahorros");
        this.cbxProductos.getItems().add("Cuenta Corriente");
        this.cbxProductos.getItems().add("CDT");
        this.cbxProductos.getItems().add("Tarjeta Visa");
        this.cbxProductos.getItems().add("Tarjeta American");
          
    }
    private void modelaTabla()
    {

        this.colIdProducto.setCellValueFactory(new PropertyValueFactory("IdProducto"));
        this.colIdCliente.setCellValueFactory(new PropertyValueFactory("IdCliente"));
        this.colProducto.setCellValueFactory(new PropertyValueFactory("producto"));
        this.colApertura.setCellValueFactory(new PropertyValueFactory("Fecha"));
        this.colDisponibilidad.setCellValueFactory(new PropertyValueFactory("Valor1"));
    }
  
}
