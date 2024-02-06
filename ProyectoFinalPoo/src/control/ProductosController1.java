/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
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
public class ProductosController1 implements Initializable {

    @FXML
    private ComboBox<String> cbxProductos;
    @FXML
    private Button verTodos;
    private String idCliente;
    @FXML
    private Label btnLupa;
    @FXML
    private Label regresar;
    @FXML
    private Button crear;
    @FXML
    private Button modificar;
    @FXML
    private Button elimina;
    @FXML
    private TextField numeroProducto;
    @FXML
    private Label label1;
    @FXML
    private TextField txt1;
    @FXML
    private Label label2;
    @FXML
    private TextField txt2;
    
    private ClienteController cliCont;
    
    private GestionClientes gesCli;
    private GestionProductos gesPro;
    
    private Producto producto;
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;

    private String idProductoActual;
    private String idActual;
    private int pos;
    private boolean existe;
    
    private StringBuilder stringBuilderTxt1;
    private StringBuilder stringBuilderTxt2;
    private StringBuilder stringBuilderIdCliente;
    
    @FXML
    private TextField txtidCliente;
    @FXML
    private DatePicker fechaCreacionProducto;
    @FXML
    private Label reqFecha;
    @FXML
    private Label reqTxt1;
    @FXML
    private Label reqTxt2;
    
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.gesCli = new GestionClientes();
        this.cliCont = new ClienteController();
        this.gesPro = new GestionProductos();
        
        this.clientes = new ArrayList<>(); // Inicializa la lista de clientes
        this.productos = new ArrayList<>();// Inicializa la lista de productos
        
        this.fechaCreacionProducto.setDisable(true);
        
        this.traeClientes();
    }    

      
    @FXML
    private void doVerTodos(MouseEvent event) 
    {
        try 
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/AllProductos.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);
            this.traeProductos();
            AllProductosController controlVentana2=loader.getController();
            controlVentana2.setProductos(this.productos);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Listado De Productos");
            stage.show();
            Stage myStage = (Stage)this.verTodos.getScene().getWindow();
            myStage.close();  
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(AllClientesController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    
    }
    
   

    @FXML
    private void doIdCliente(MouseEvent event) 
    {
        
        String idCliente;
        boolean existe =false;
        cbxProductos.getItems().clear();
        this.limpiaTodo();
        cbxProductos.setPromptText("Productos");
        idCliente=this.txtidCliente.getText();
        this.fechaCreacionProducto.setDisable(true);
          
        if(idCliente.isEmpty())
        {
            this.crear.setDisable(true);
            this.modificar.setDisable(true);
            this.elimina.setDisable(true);
            this.cliCont.manageMessages(1,"Debe digitar un ID");
        }
        
        else//si el campo no está vacio
        {
            this.traeClientes();
            if(this.clientes.isEmpty())
            {
                 this.cliCont.manageMessages(1, "En este momento NO HAY CLIENTES en la base de datos");
                 this.txtidCliente.setText("");
            }
            else{
                for(Cliente cliente: this.clientes)
                {
                    if(idCliente.equals(cliente.getIdCliente()))
                    {
                          existe=true;
                          this.llenaComboBox(cliente.getProductos());
                          this.idCliente=this.txtidCliente.getText();
                          break;
                    }
                }
                if(!existe)
                {
                    this.cliCont.manageMessages(1, "Cliente NO registrado");
                    this.traeClientes();
                    this.crear.setDisable(true);
                    this.modificar.setDisable(true);
                    this.elimina.setDisable(true);
                    this.txtidCliente.setText(null);
                } 
                else
                     this.cliCont.manageMessages(2,"Despliegue la lista para ver sus productos");
            }
        }
        this.existe=existe;
    }
    

    @FXML
    private void doVerificaProductosNoVacio(MouseEvent event) 
    {
        if(this.txtidCliente.getText().isEmpty())
            this.cliCont.manageMessages(2,"Digite el numero del Id del Cliente del cual desea ver los productos" );    
        else
            if(!this.existe){
                this.cliCont.manageMessages(2,"Digite un Id de un Cliente que esté en la base de datos del banco" );    
            }
    }

    @FXML
    private void defineProductoActual(ActionEvent event)
    { 
        this.limpiaTodo();
        this.traeProductos();
        this.reqFecha.setText("");
        this.reqTxt1.setText("");
        this.reqTxt2.setText("");
        
        this.fechaCreacionProducto.setDisable(false);
        
        if(!(cbxProductos.getSelectionModel().isEmpty())) 
            {
            if(!this.txtidCliente.getText().isEmpty())
            {
                if (this.productos.isEmpty())
                {
                    this.cliCont.manageMessages(1,"El producto NO ha sido creado aún" );
                    String idProducto = String.valueOf(this.doGenerarIdProducto());
                    this.poneProductos(idProducto);
                    this.idProductoActual=idProducto;
                    this.crear.setDisable(false);
                    this.modificar.setDisable(true);
                    this.elimina.setDisable(true);
                }

                else{
                    int cont=0;
                    for(Producto producto: this.productos)
                    {
                        if ((this.idCliente.equals(producto.getIdCliente())) && (this.cbxProductos.getValue().equals(producto.getProducto())))
                        {
                           
                            this.poneProductos(producto.getIdProducto());
                            this.crear.setDisable(true);
                            this.modificar.setDisable(false);
                            this.elimina.setDisable(false);
                            this.cliCont.manageMessages(2,"El producto YA ha sido creado" );
                            this.idProductoActual=producto.getIdProducto();
                            cont=1;
                            break;
                        }
                    }
                    if(cont!=1)
                    {
                         this.crear.setDisable(false);
                         this.modificar.setDisable(true);
                         this.elimina.setDisable(true);
                         this.cliCont.manageMessages(1,"El producto NO ha sido creado aún" );
                         String idProducto = String.valueOf(this.doGenerarIdProducto());
                         this.poneProductos(idProducto);
                         this.idProductoActual=idProducto;
                    }

                }
            }
        }
    }

    @FXML
    private void doCrear(MouseEvent event) {
        
        String errores=this.verificaDatos();
        // Guardar PRODUCTO
        if (errores.isEmpty()) 
        {
             
            String fecha = null;
            String txt2 = "0";
            if(this.txt2.getText().isEmpty()){
                txt2 = "0";
            }
            else
            {
                txt2=this.txt2.getText();
            }
            int tipoProducto = this.deStringInt(this.cbxProductos.getValue());
            String txt1 = this.txt1.getText();
            LocalDate fechaCreacion = this.fechaCreacionProducto.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            fecha = fechaCreacion.format(formatter);
            this.reqFecha.setText(" ");
            this.reqTxt1.setText(" ");
            this.reqTxt2.setText(" ");

            Producto producto = new Producto(this.numeroProducto.getText(), tipoProducto, this.idCliente, fecha, Float.parseFloat(txt1), Integer.parseInt(txt2));

            if (this.gesPro.guardaProducto(producto)) {
                this.cliCont.manageMessages(2, "Producto GUARDADO con ÉXITO");
                this.traeClientes();
                this.crear.setDisable(true);
                this.modificar.setDisable(false);
                this.elimina.setDisable(false);
            } else {
                this.cliCont.manageMessages(1, "FALLO creando el Producto, revise los datos");
            }
        } 
        else {
            this.cliCont.manageMessages(1, errores);
        }
    }

    @FXML
    private void doModificar(MouseEvent event) 
    {
        String errores=this.verificaDatos();
        if (errores.isEmpty())  
        {   
            boolean seguro=false;
            this.traeProductos(); 
            String fecha = null;
            
            String tipoProducto = this.cbxProductos.getValue();
            String txt1 = this.txt1.getText();
            String txt2="0";
            LocalDate fechaCreacion = this.fechaCreacionProducto.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            fecha = fechaCreacion.format(formatter);
            seguro=this.cliCont.manageMessages(3,"¿Está seguro de que quiere MODIFICAR el producto?");
                       
            for(Producto producto: this.productos)
                {   
                     if(tipoProducto.equals("CDT"))
                     {
                        txt2 = this.txt2.getText();
                     }
                        
                    if ((this.idCliente.equals(producto.getIdCliente())) && (this.cbxProductos.getValue().equals(tipoProducto)))
                     {
                        
                        if(seguro==true)
                        {
                            this.reqFecha.setText("");
                            this.reqTxt1.setText("");
                            this.reqTxt2.setText("");
                            producto.setFecha(fecha);
                            producto.setValor1( Float.parseFloat(txt1));
                            producto.setValor2(Integer.parseInt(txt2));
                            this.gesPro.recargaArchivo(this.productos);
                            this.traeProductos();
                                }
                    }
                }
            if(seguro==true)
                this.cliCont.manageMessages(2,"El PRODUCTO fue MODIFICADO");        
        }
        else{
        this.cliCont.manageMessages(1, errores);
        }
    }

    @FXML
    private void doEliminar(MouseEvent event) 
    {
       String errores=this.verificaDatos();
      
        if (this.cbxProductos.getItems().size()==1)
        {
            this.cliCont.manageMessages(1, "Este es el ÚNICO PRODUCTO del CLIENTE! NO se puede borrar");
        }

        else
        {
        
        boolean seguro;
        seguro=this.cliCont.manageMessages(3,"¿Está seguro de que quiere ELIMINAR el producto?");
        if (seguro==true)
        {              
            this.traeProductos();
            for(Producto producto:this.productos)
            {
                if (this.idCliente.equals(producto.getIdCliente()) && this.cbxProductos.getValue().equals(producto.getProducto()))
                { 
                    productos.remove(producto);
                    this.cliCont.manageMessages(2,"El PRODUCTO fue ELIMINADO");
                    this.gesPro.recargaArchivo(this.productos);
                    this.traeProductos(); 
                    this.limpiaTodo();
                    this.modificar.setDisable(true);
                    this.elimina.setDisable(true);
                    break;
                 }
            }

        } 
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente().equals(this.idCliente)) {
                boolean[] productosCliente = cliente.getProductos();

                switch (this.cbxProductos.getValue()) {
                    case "Cuenta Ahorros":
                        productosCliente[0] = false;
                        break;
                    case "Cuenta Corriente":
                        productosCliente[1] = false;
                        break;
                    case "CDT":
                        productosCliente[2] = false;
                        break;
                    case "Tarjeta Visa":
                        productosCliente[3] = false;
                        break;
                    case "Tarjeta American":
                        productosCliente[4] = false;
                        break;
                }
                cliente.setProductos(productosCliente);
                this.gesCli.recargaArchivo(this.clientes);
                this.cbxProductos.getItems().clear();
                this.llenaComboBox(productosCliente);
                this.fechaCreacionProducto.setDisable(true);
            }
        }        
       }
    }
    @FXML
    private void doVerificacionTxt1(KeyEvent e) 
    {
        this.stringBuilderTxt1 = new StringBuilder(this.txt1.getText());
        char c = e.getCharacter().charAt( 0); 
        if ((!Character.isDigit(c))&&(c!= java.awt.event.KeyEvent.VK_DELETE)&&(c!=java.awt.event.KeyEvent.VK_BACK_SPACE)&&(c!='.'))
        { 
             int posCursor=this.txt1.getCaretPosition(); //obtiene posición de cursor en el momento de digitar el caracter
             if(posCursor>0)
            {
             this.stringBuilderTxt1.deleteCharAt(posCursor - 1);
             this.txt1.setText(this.stringBuilderTxt1.toString());
             this.txt1.positionCaret(posCursor - 1);
            }
          this.cliCont.manageMessages(1, "Caracter no válido en este campo"); 
        }
    }

    @FXML
    private void doVerificacionTxt2(KeyEvent e) 
    {
        this.stringBuilderTxt2 = new StringBuilder(this.txt2.getText());
        char c = e.getCharacter().charAt( 0); 
        if ((!Character.isDigit(c))&&(c!= java.awt.event.KeyEvent.VK_DELETE)&&(c!=java.awt.event.KeyEvent.VK_BACK_SPACE)&&(c!='.'))
        { 
             int posCursor=this.txt2.getCaretPosition(); //obtiene posición de cursor en el momento de digitar el caracter
             if(posCursor>0)
            {
             this.stringBuilderTxt2.deleteCharAt(posCursor - 1);
             this.txt2.setText(this.stringBuilderTxt2.toString());
             this.txt2.positionCaret(posCursor - 1);
            }
          this.cliCont.manageMessages(1, "Caracter no válido en este campo"); 
        }
    }
    
     @FXML
    private void doVerificaIdCliente(KeyEvent e) 
    {
        this.stringBuilderIdCliente = new StringBuilder(this.txtidCliente.getText());
        char c = e.getCharacter().charAt( 0); 
        if (((!Character.isDigit(c)))&&(c!= java.awt.event.KeyEvent.VK_DELETE)&&(c!=java.awt.event.KeyEvent.VK_BACK_SPACE))
        { 
             int posCursor=this.txtidCliente.getCaretPosition(); //obtiene posición de cursor en el momento de digitar el caracter
             if(posCursor>0)
             {
             this.stringBuilderIdCliente.deleteCharAt(posCursor - 1);
             this.txtidCliente.setText(this.stringBuilderIdCliente.toString());
             this.txtidCliente.positionCaret(posCursor - 1);
             }
          this.cliCont.manageMessages(1, "Caracter no válido en este campo"); 
        }
    }
    
    
    @FXML
    private void doNumeroProducto(MouseEvent event) {
        this.cliCont.manageMessages(2,"Por seguridad, NO está permitido que el usuario modifique número del producto");
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

            Stage myStage = (Stage)this.regresar.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
           //Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }
    
    //AUXILIARES========================================================================================

    private String doGenerarIdProducto() 
    {
        String numRandom;
        Random random = new Random();
        long min = 100_000_000L; 
        long max = 999_999_999L; 
        numRandom=(min + ((long) (random.nextDouble() * (max - min))))+"";
        for(Producto producto: this.productos)
        {
            if(numRandom.equals(producto.getIdProducto()))
            {
                this.doGenerarIdProducto();
            }
        }
        return numRandom ;
    }
    
    private void poneProductos(String idProducto)
    {
        this.txt2.setText("");       
        this.numeroProducto.setText(idProducto);
        this.defineTipoProducto();
        
        for(Producto producto: this.productos)
        {     
           if(producto.getIdProducto().equals(idProducto))
            {
                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                  String date = producto.getFecha();
                  this.txt1.setText(producto.getValor1()+"");
                  this.fechaCreacionProducto.setValue(LocalDate.parse(date,formatter));
                  if(producto.getTipoProducto()==2)
                  {
                      this.txt2.setText(producto.getValor2()+"");
                  }
            }  
        }
        
     }  
     
    
    private void llenaComboBox(boolean [] productos)
    {
       
        if(productos[0]==true)
        {    
            this.cbxProductos.getItems().add("Cuenta Ahorros");

        }
        if(productos[1]==true)
        {
             this.cbxProductos.getItems().add("Cuenta Corriente");
        }
        if(productos[2]==true)
        {
            this.cbxProductos.getItems().add("CDT");
        }
        if(productos[3]==true)
        {
            this.cbxProductos.getItems().add("Tarjeta Visa");
        }   
        
        if(productos[4]==true)
        {
            this.cbxProductos.getItems().add("Tarjeta American");
        } 
    }
      
    
    private void traeClientes()
    {
        this.clientes=this.gesCli.getTodos();
    }
    
    private void traeProductos()//llama todos los productos y hace las respectivas verificaciones
    {
        this.productos=this.gesPro.getTodos();
        this.verificaClientesExistentes();
        this.verificaProductosActualesCliente();
    }
    
    public void verificaProductosActualesCliente()
    {
//      VERIFICA QUE CUANDO SE Modifiquen atributos de cliente PRODUCTOS, Si el producto ya estaba creado se borra
        if(this.productos!=null)
        {
            List<Producto> productosTemp = new ArrayList<>();
            for(Producto producto:this.productos)
            {
               for(Cliente cliente:this.clientes)
                {
                     if(cliente.getIdCliente().equals(producto.getIdCliente()))
                     {
                         boolean productosCliente[];
                         productosCliente=cliente.getProductos();
                         
                         if(producto.getTipoProducto()==0 && productosCliente[0]==true)
                         {
                             productosTemp.add(producto);
                         }
                         if(producto.getTipoProducto()==1 && productosCliente[1]==true)
                         {
                             productosTemp.add(producto);
                         }
                         if(producto.getTipoProducto()==2 && productosCliente[2]==true)
                         {
                             productosTemp.add(producto);
                         }
                         if(producto.getTipoProducto()==3 && productosCliente[3]==true)
                         {
                             productosTemp.add(producto);
                         }
                         if(producto.getTipoProducto()==4 && productosCliente[4]==true)
                         {
                             productosTemp.add(producto);
                         }
                     }
                }
            }
            productos.clear();
            productos.addAll(productosTemp);
            this.gesPro.recargaArchivo(productos);
        }
    }
    
    private void verificaClientesExistentes(){
        
       // Elimina Cliente, Elimina Productos que tenia CREADOS el cliente
       List<Producto> productosTemp = new ArrayList<>();

        for (Cliente cliente : this.clientes) {
                // Cliente no es el que se va a eliminar, agregar sus productos a la lista temporal
                for (Producto producto : productos) {
                    if (producto.getIdCliente().equals(cliente.getIdCliente())) {
                        productosTemp.add(producto);
                    }
                }
        }
        productos.clear();
        productos.addAll(productosTemp);
        this.gesPro.recargaArchivo(productos);
    }
    
    //Si se cambia id cliente, se llama este metodo indicando el nuevo id
    public ArrayList<Producto> changeId(String idViejo, String idNuevo, ArrayList<Producto> producto)
    {
        if(producto!=null)
        {
            for(Producto prod: producto)
            {
                if(prod.getIdCliente().equals(idViejo))
                {
                    prod.setIdCliente(idNuevo);
                }
            }
        }
        return producto;
        
    }

    private void defineTipoProducto()
    {
       String tipoProducto =this.cbxProductos.getValue();
       
       if(tipoProducto.equals("Cuenta Ahorros")||tipoProducto.equals("Cuenta Corriente"))
       {
           this.label1.setText("Saldo (miles)💰");
           this.txt1.setDisable(false);
           this.txt2.setDisable(true);
       }
       if(tipoProducto.equals("CDT"))
       {
           this.label2.setText("Plazo (meses) 📅");
           this.txt2.setDisable(false);
           this.label1.setText("Inversión (miles)💰");
           this.txt1.setDisable(false);
       }
       if(tipoProducto.equals("Tarjeta Visa")||tipoProducto.equals("Tarjeta American"))
       {
           this.label1.setText("Cupo (miles)💰");
           this.txt1.setDisable(false);
           this.txt2.setDisable(true);
       }
    }  
    
    
    private void limpiaTodo()
    {
        this.label1.setText("");
        this.label2.setText("");
        this.txt1.setText("");
        this.txt2.setText("");
        this.numeroProducto.setText("");
        fechaCreacionProducto.setValue(null);
        
    }
    
    private String verificaDatos()
    {
        // Verificación de campos vacíos
        String errores = "";
        String fecha = null;
        String txt1 = "0";
        String txt2 = "0";
        String tipoProducto = this.cbxProductos.getValue();

        // Verificación FECHA
            LocalDate fechaCreacion = this.fechaCreacionProducto.getValue();
            if (fechaCreacion == null)
            {
                errores += "\nDebe digitar la FECHA de creación del producto\n";
                this.reqFecha.setText("*Requerido");
            } 
            else 
            {
                LocalDate fechaActual = LocalDate.now();
                if (fechaCreacion.isBefore(fechaActual)) 
                {
                    errores += "\nLa fecha NO puede ser anterior a la fecha actual\n";
                    this.reqFecha.setText("*Requerido");
                } 
                else 
                {
                    // Formatear la fecha en un formato específico (puedes ajustar el formato)
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    fecha = fechaCreacion.format(formatter);
                    this.reqFecha.setText("");
                }
            }
        

        // VERIFICACIÓN CUENTAS
            if (tipoProducto.equals("Cuenta Ahorros") || tipoProducto.equals("Cuenta Corriente")) {
                if (this.txt1.getText() == null || this.txt1.getText().isEmpty()) {
                    errores += "\nDebe digitar el SALDO del producto\n";
                    this.reqTxt1.setText("*Requerido");
                } 
                else 
                {
                    if(Float.parseFloat(this.txt1.getText())<0)
                    {
                        errores+="El SALDO NO puede ser menor a 0 \n";
                    }
                    else
                    {
                        txt1 = this.txt1.getText();
                        this.reqTxt1.setText("");
                    }
                }
            }
        
        
       // VERIFICACIÓN TARJETAS
            if (tipoProducto.equals("Tarjeta Visa") || tipoProducto.equals("Tarjeta American")) {
                if (this.txt1.getText() == null || this.txt1.getText().isEmpty()) {
                    errores += "\nDebe digitar el CUPO del producto\n";
                    this.reqTxt1.setText("*Requerido");
                } 
                else 
                {
                    if(Float.parseFloat(this.txt1.getText())<0)
                    {
                        errores+="El CUPO NO puede ser menor a 0 \n";
                        this.reqTxt1.setText("*Requerido");
                    }
                    else
                    {
                        txt1 = this.txt1.getText();
                        this.reqTxt1.setText("");
                    }
                }
            }

        // VERIFICACIÓN CDT
            if (tipoProducto.equals("CDT")) 
            {
                if (this.txt1.getText() == null || this.txt1.getText().isEmpty()) {
                    errores += "\nDebe digitar la INVERSIÓN del producto\n";
                    this.reqTxt1.setText("*Requerido");
                } 
                else 
                {
                    if(Float.parseFloat(this.txt1.getText())<=0)
                    {
                        errores+="La INVERSIÓN NO puede ser menor o igual a 0 \n";
                        this.reqTxt1.setText("*Requerido");
                    }
                    else
                    {
                        txt1 = this.txt1.getText();
                        this.reqTxt1.setText("");

                    }
                }
            
            if (this.txt2.getText() == null || this.txt2.getText().isEmpty()) {
                errores += "\nDebe digitar el PLAZO del producto\n";
                this.reqTxt2.setText("*Requerido");
            } 
            else 
            {
                if(Integer.parseInt(this.txt2.getText())<0&& tipoProducto=="CDT")
                {
                    errores+="El PLAZO NO puede ser menor a 0 \n";
                    this.reqTxt2.setText("*Requerido");
                }
                else
                {
                    txt2 = this.txt2.getText();
                    this.reqTxt2.setText("");
                }
            }
        }
        return errores;  
    }
    
   
    public int deStringInt(String tipoProductoString)
    {
        int tipoProducto=-1;
        
        switch(tipoProductoString)
        {
            case "Cuenta Ahorros":
                tipoProducto=0 ;
                break;
            case "Cuenta Corriente":
                tipoProducto = 1;
                break;
            case "CDT":
                tipoProducto = 2;
                break;
            case "Tarjeta Visa":
                tipoProducto= 3;
                break;
            case "Tarjeta American":
                tipoProducto = 4;
                break;
            default:
                tipoProducto = -1;
                break;
        }
        return tipoProducto;
    }

   
}