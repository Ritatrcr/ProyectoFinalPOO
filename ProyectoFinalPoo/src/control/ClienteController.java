/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class ClienteController implements Initializable {

    @FXML
    private TextField txtNombre;
    @FXML
    private RadioButton rdbHombre;
    @FXML
    private RadioButton rdbOtro;
    @FXML
    private RadioButton rdbMujer;
    @FXML
    private CheckBox chbxCuentaAhorros;
    @FXML
    private CheckBox chbxTarjetaVisa;
    @FXML
    private Button buscarId;
    @FXML
    private CheckBox chbxTarjetaAmerica;
    private Button btn_Salir;
    @FXML
    private ImageView img_Foto;
    
    
    private String laFoto;
    private String rutaImages;
    private GestionClientes gesCli;
    private GestionProductos gesPro;
    private ArrayList<Cliente> clientes;
    private ArrayList<Producto> productos;
    private Cliente clienteActual;
    private int pos;
    private String idActual;
    private String numeroIdActual;
    
    @FXML
    private TextField txtIdCliente;
    @FXML
    private CheckBox chbxCDT;
    @FXML
    private CheckBox chbxCuentaCorriente;
    @FXML
    private AnchorPane panePrincipal;
    @FXML
    private Label btnRegresar;
    
    @FXML
    private TextField textFieldClave;
    private ProductosController1 proCont;
    @FXML
    private Pane paneBlanco;
    @FXML
    private Button guardar;
    @FXML
    private Button modificar;
    
    private StringBuilder stringBuilderNombre;
    private StringBuilder stringBuilderIdCliente;

    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gesCli=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.proCont = new ProductosController1();
        this.pos=0;
        this.rutaImages="././ImagesClientes";
        this.laFoto="sinfoto.png";
        
        ToggleGroup tg = new ToggleGroup();
        this.rdbHombre.setToggleGroup(tg);
        this.rdbMujer.setToggleGroup(tg);
        this.rdbOtro.setToggleGroup(tg);
        this.rdbOtro.setSelected(true);
        
        this.traeClientes();
        
        if(this.idActual.equals("new"))
        {
            this.modificar.setDisable(true);
            this.guardar.setDisable(false);
        }
        else{
            this.modificar.setDisable(false);
            this.guardar.setDisable(true);
        }
    }    

    @FXML
    private void doLimpiar(ActionEvent event)
    {
        this.txtIdCliente.clear();
        this.txtNombre.clear();
        this.textFieldClave.clear();
        
         this.rdbHombre.setSelected(false);
         this.rdbMujer.setSelected(false);
         this.rdbOtro.setSelected(true);
        
        this.limpiaChecks();
        
        this.laFoto="sinfoto.png";
        this.poneFoto();
        
        this.txtIdCliente.requestFocus();
        this.idActual="new";
        
        this.modificar.setDisable(true);
        this.guardar.setDisable(false);

        
    }
    
     @FXML
    private void doSeleccionarFoto(ActionEvent event) 
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Buscar Imagen");
        fc.setInitialDirectory(new File(this.rutaImages));
        File imgFile=fc.showOpenDialog(new Stage());
        this.laFoto=imgFile.getName();
        Image image = new Image("file:"+imgFile.getAbsolutePath());
        this.img_Foto.setImage(image); 
    }
    
    @FXML
    private void doGuardar(ActionEvent event)
    {
        String id,name,clave;
        char genero = '*';
        boolean productos[] = {false,false,false,false,false}; 
        String errores = "";
        int conta = 0;
        boolean existe;
        boolean existeClave=false;
        
        id = this.txtIdCliente.getText();
        if(id.isEmpty()){
            errores += "Por favor ingrese un ID\n";
        }
        
        name = this.txtNombre.getText();
        if(name.isEmpty()){
            errores += "Por favor ingrese un Nombre\n";
        }
        
        clave = this.textFieldClave.getText();

        if(clave.isEmpty()){
            errores += "Por favor ingrese una Clave\n";
        }
        
        
        //Verificar los radio butons
        if(this.rdbHombre.isSelected()){
            genero = 'H';
        }
        if(this.rdbMujer.isSelected()){
            genero = 'M';
        }
        if(this.rdbOtro.isSelected()){
            genero = 'O';
        }
        
        //Verificar los check butons
        if(this.chbxCuentaAhorros.isSelected()){
            productos[0] = true;
        }
        if(this.chbxCuentaCorriente.isSelected()){
            productos[1] = true;
        }
        if(this.chbxCDT.isSelected()){
            productos[2] = true;
        }
        if(this.chbxTarjetaVisa.isSelected()){
            productos[3] = true;
        }
        if(this.chbxTarjetaAmerica.isSelected()){
            productos[4] = true;
        }
        
        //Verificar si se selecciono un check button
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == true){
                conta++;
            }    
        }
      
        if(conta == 0){
            errores += "Debes haber seleccionado al menos un producto...\n";
        }
       
        if(errores.length()==0)
        { 
            for(Cliente cliente: this.clientes)
            {
                if (this.textFieldClave.getText().equals(cliente.getClave()))
                {
                    existeClave=true;
                    break;
                }
            }
            
            Cliente cliente = new Cliente (id,name,this.laFoto,genero,productos,clave);
            {
                if (this.gesCli.pruebaExistencia(id) )//revisar
                    this.manageMessages(1,"¡Lo sentimos! Ese ID ya existe");
                else
                {
                    if(existeClave)
                    {
                        this.manageMessages(1,"¡Lo sentimos! Esa CLAVE ya existe");
                    }
                    else{
                    
                        if(this.gesCli.guardaCliente(cliente) == true)
                        {
                             this.manageMessages(2,"Cliente guardado con éxito");
                             this.traeClientes();
                             this.modificar.setDisable(false);
                             this.guardar.setDisable(true);
                        }
                        else
                            this.manageMessages(1,"Fallo creando el cliente, revise los datos");
                    }
                }
            }
        }
        else
        {
             this.manageMessages(1, errores);
        }
    }
            
// modificar tambien productos
            
    @FXML
    private void doModificar(ActionEvent event)
    {     
        String id,name,clave;
        char genero = '*';
        boolean productos[] = {false,false,false,false,false}; 
        String errores = "";
        int conta = 0;
        boolean existe;
        
        id = this.txtIdCliente.getText();
        if(id.isEmpty()){
            errores += "Por favor ingrese un ID\n";
        }
        
        name = this.txtNombre.getText();
        if(name.isEmpty()){
            errores += "Por favor ingrese un Nombre\n";
        }
        
        clave = this.textFieldClave.getText();
        
        
        if(clave.isEmpty()){
            errores += "Por favor ingrese una Clave\n";
        }
        
        
        //Verificar los radio butons
        if(this.rdbHombre.isSelected()){
            genero = 'H';
        }
        if(this.rdbMujer.isSelected()){
            genero = 'M';
        }
        if(this.rdbOtro.isSelected()){
            genero = 'O';
        }
        
        //Verificar los check butons
        if(this.chbxCuentaAhorros.isSelected()){
            productos[0] = true;
        }
        if(this.chbxCuentaCorriente.isSelected()){
            productos[1] = true;
        }
        if(this.chbxCDT.isSelected()){
            productos[2] = true;
        }
        if(this.chbxTarjetaVisa.isSelected()){
            productos[3] = true;
        }
        if(this.chbxTarjetaAmerica.isSelected()){
            productos[4] = true;
        }
        
        //Verificar si se selecciono un check button
        for (int i = 0; i < productos.length; i++) {
            if (productos[i] == true){
                conta++;
            }    
        }
      
        if(conta == 0){
            errores += "Debe seleccionar al menos un producto...\n";
        }
        
        
       boolean existeClave=false;
       for(Cliente cliente: this.clientes)
            {
                if ((this.textFieldClave.getText().equals(cliente.getClave()))&& (!cliente.equals(this.clienteActual)))
                {
                    existeClave=true;
                    break;
                }
            }
       
        if(errores.length()==0)
        { //PARA MODIFICACIONES
            boolean seguro=this.manageMessages(3,"¿Está seguro que desea mdoificar los datos del CLIENTE?");

            if((this.gesCli.pruebaExistencia(id)) && (!this.txtIdCliente.getText().equals(this.numeroIdActual))) 
            {
                this.manageMessages(1,"MODIFICACIÓN CANCELADA!\nEse ID YA existe");
                this.txtIdCliente.setText(this.clienteActual.getIdCliente());
            }
            else
            {   //verifica que no exista clave
                if(existeClave)
                {
                    this.manageMessages(1,"MODIFICACIÓN CANCELADA!\nEsa CLAVE YA existe");
                    this.textFieldClave.setText(this.clienteActual.getClave());  
                }
                else
                {
                    if(seguro)
                    {
                        Iterator<Cliente> iter = this.clientes.iterator();
                        int pos = -1;

                        while(iter.hasNext())
                        {
                            pos++;
                            if(iter.next().getIdCliente().equals(this.idActual))
                            {
                                iter.remove();
                                ArrayList<Producto> producto=this.gesPro.getTodos();
                                producto=this.proCont.changeId(clienteActual.getIdCliente(),id,producto);
                                this.gesPro.recargaArchivo(producto);
                                Cliente cliente = new Cliente (id,name,this.laFoto,genero,productos,clave);
                                this.clientes.add(pos,cliente);

                                break;
                            }
                        }
                        this.gesCli.recargaArchivo(this.clientes);
                        this.manageMessages(2,"El cliente ha sido modificado");

                        this.traeClientes();
                    }     
                } 
            }
        }
        
        else
        {
            this.manageMessages(1, errores);
        }
    }
    
    

    
    @FXML
    private void doBuscar(ActionEvent event) 
    {
        String id;
        int pos=-1;
        boolean existe =false;
        
        id=this.txtIdCliente.getText();
        if(id.isEmpty())
        {
            this.manageMessages(1,"Debe digitar un ID");
        }
        else
        {
            for(Cliente cliente: this.clientes)
            {
                pos++;
                if(cliente.getIdCliente().equals(id))
                {
                    this.poneCliente(cliente);
                    this.idActual=cliente.getIdCliente();
                    this.pos=pos;
                    existe=true;
                    this.clienteActual=cliente;
                    this.modificar.setDisable(false);
                    this.guardar.setDisable(true);
                    break;
                }
                
            }
            if(!existe)
                this.manageMessages(1, "NO están registrados los datos");
        }
        
        
    }
    
    
    
    

    @FXML
    private void doAnterior(ActionEvent event) 
    {
        if(this.pos>0)
        {
            this.pos--;
            Cliente anterior=this.clientes.get(this.pos);
            this.poneCliente(anterior);
            this.idActual=anterior.getIdCliente();
             this.modificar.setDisable(false);
             this.guardar.setDisable(true);
        }
    }
    
    @FXML
    private void doSiguiente(ActionEvent event) 
    {
        if(this.pos<this.clientes.size()-1)
        {
            this.pos++;
            Cliente siguiente=this.clientes.get(this.pos);
            this.poneCliente(siguiente);
            this.idActual=siguiente.getIdCliente();
            this.modificar.setDisable(false);
            this.guardar.setDisable(true);
        }
    }
 
    @FXML
    private void doEliminar(ActionEvent event) 
    {
        boolean seguro;
        
        seguro=this.manageMessages(3, "Confirma la operación?");
        if (seguro)
        {
            Iterator<Cliente> iter=this.clientes.iterator();
            while(iter.hasNext())
            {
                if(iter.next().getIdCliente().equals(this.idActual))
                {
                    iter.remove();
                    break;
                }
            }
            
            this.gesCli.recargaArchivo(this.clientes);
            this.manageMessages(2, "Los datos del Cliente han sido removidos");
            this.traeClientes();
            if (this.gesCli.getTodos().isEmpty()){
                 this.txtIdCliente.clear();
            this.txtNombre.clear();
            this.textFieldClave.clear();

             this.rdbHombre.setSelected(false);
             this.rdbMujer.setSelected(false);
             this.rdbOtro.setSelected(true);

            this.limpiaChecks();

            this.laFoto="sinfoto.png";
            this.poneFoto();

            this.txtIdCliente.requestFocus();
            this.idActual="new";
            
            if(this.clientes.isEmpty())
            {
                this.modificar.setDisable(true);
                this.guardar.setDisable(false);
            }
                
            }
        }
         
    }
    
    @FXML
    private void doVerTodo(ActionEvent event) throws IOException 
    {
        
        
        try 
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/AllClientes.fxml"));
            Parent root=loader.load();
            Scene scene=new Scene(root);

            AllClientesController controlVentana2=loader.getController();
            controlVentana2.setClientes(this.clientes);


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setOnCloseRequest(even ->{even.consume();}); //Deshabilita la X para salir
            stage.setResizable(false);
            stage.setTitle("Listado De Clientes");
            stage.show();

            Stage myStage = (Stage)this.btn_Salir.getScene().getWindow();
            myStage.close();  
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(AllClientesController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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

//AUXILIARES======================================================================================
    private void traeClientes()
    {
        this.pos=0;
        this.clientes=this.gesCli.getTodos();
        if(!this.clientes.isEmpty())
        {
            Cliente primero=this.clientes.get(this.pos);
            this.poneCliente(primero);
            this.idActual=primero.getIdCliente();
        }
        else
        {
            this.idActual="new";
        }
        
    }
    
    private void poneCliente(Cliente cliente)
    {
        boolean productos[];
        this.limpiaChecks();
        
        this.clienteActual=cliente;
        //Nombre && ID
        this.numeroIdActual=cliente.getIdCliente();
        this.txtIdCliente.setText(cliente.getIdCliente());
        this.txtNombre.setText(cliente.getNombre());
        this.textFieldClave.setText(cliente.getClave());
    
        
        //GÉNERO
        if(cliente.getGenero()=='H' )
        {
            this.rdbHombre.setSelected(true);
        }
        if(cliente.getGenero()=='M' )
        {
            this.rdbMujer.setSelected(true);
        }
        if(cliente.getGenero()=='O' )
        {
            this.rdbOtro.setSelected(true);
        }
        
        //Productos
        productos=cliente.getProductos();
        if(productos[0]==true)
        {
            this.chbxCuentaAhorros.setSelected(true);
        }
        if(productos[1]==true)
        {
            this.chbxCuentaCorriente.setSelected(true);
        }
        if(productos[2]==true)
        {
            this.chbxCDT.setSelected(true);
        }
        if(productos[3]==true)
        {
            this.chbxTarjetaVisa.setSelected(true);
        }
        if(productos[4]==true)
        {
            this.chbxTarjetaAmerica.setSelected(true);
        }
        
        //Foto
        this.laFoto=cliente.getFoto();
        this.poneFoto();
    
    }  
    
    private void limpiaChecks()
    {
         this.chbxCuentaAhorros.setSelected(false);
         this.chbxCuentaCorriente.setSelected(false);
         this.chbxCDT.setSelected(false);
         this.chbxTarjetaVisa.setSelected(false);
         this.chbxTarjetaAmerica.setSelected(false);
    }
    
    private void poneFoto()//Pone la foto del cliente actual
        
    {
       Image image = new Image("file:" +"././ImagesClientes/"+ this.laFoto);
       img_Foto.setImage(image);

    }              
 
    
    public boolean manageMessages(int caso,String message)
    {
        Alert msg;
        boolean ok = false;
        if(caso == 1){//Error
            msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("Error");
            msg.setHeaderText(null);
            msg.setContentText(message);
            msg.showAndWait();
        }
        if(caso == 2){//Notificación
            msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Listo");
            msg.setHeaderText(null);
            msg.setContentText(message);
            msg.showAndWait();
        }
        if(caso == 3)
        {//Confirmación
            msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Petición ");
            msg.setHeaderText(null);
            msg.setContentText(message);
            msg.initStyle(StageStyle.UTILITY);
            Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK){
                ok = true;
            }
        }
        
        return ok;
    }

    @FXML
    private void doVerificacionNombre(KeyEvent e) //Verifica input del texted field nombre
    {
         this.stringBuilderNombre = new StringBuilder(this.txtNombre.getText());
        char c = e.getCharacter().charAt( 0); 
        if (Character.isDigit(c))
        { 
             int posCursor=this.txtNombre.getCaretPosition(); //obtiene posición de cursor en el momento de digitar el caracter
             if(posCursor>0)
            {
             this.stringBuilderNombre.deleteCharAt(posCursor - 1);
             this.txtNombre.setText(this.stringBuilderNombre.toString());
             this.txtNombre.positionCaret(posCursor - 1);
            }
          this.manageMessages(1, "Caracter no válido en este campo"); 
        }
    }

    @FXML
    private void doVerificacionIdCliente(KeyEvent e) //verifica input del textfield idCliente
    {
        this.stringBuilderIdCliente = new StringBuilder(this.txtIdCliente.getText());
        char c = e.getCharacter().charAt( 0); 
        
        if ((!Character.isDigit(c))&&(c!= java.awt.event.KeyEvent.VK_DELETE)&&(c!=java.awt.event.KeyEvent.VK_BACK_SPACE)&&(c!=java.awt.event.KeyEvent.VK_ENTER))
        { 
             int posCursor=this.txtIdCliente.getCaretPosition(); //obtiene posición de cursor en el momento de digitar el caracter
             if(posCursor>0)
            {
             this.stringBuilderIdCliente.deleteCharAt(posCursor - 1);
             this.txtIdCliente.setText(this.stringBuilderIdCliente.toString());
             this.txtIdCliente.positionCaret(posCursor - 1);
            }
          this.manageMessages(1, "Caracter no válido en este campo"); 
        }
    }
}

