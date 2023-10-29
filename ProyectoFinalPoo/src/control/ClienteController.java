/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocio.Cliente;

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
    @FXML
    private Button btn_Salir;
    @FXML
    private ImageView img_Foto;
    
    
    private String laFoto;
    private String rutaImages;
    private GestionClientes gesCli;
    private ArrayList<Cliente> clientes;
    private int pos;
    private String idActual;
    @FXML
    private TextField txtIdCliente;
    @FXML
    private CheckBox chbxCDT;
    @FXML
    private CheckBox chbxCuentaCorriente;
    @FXML
    private AnchorPane panePrincipal;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.gesCli=new GestionClientes();
        this.pos=0;
        this.rutaImages="././Images";
        this.laFoto="sinfoto.png";
        
        ToggleGroup tg = new ToggleGroup();
        this.rdbHombre.setToggleGroup(tg);
        this.rdbMujer.setToggleGroup(tg);
        this.rdbOtro.setToggleGroup(tg);
        this.rdbOtro.setSelected(true);
        
        this.traeClientes();
    }    

    @FXML
    private void doLimpiar(ActionEvent event)
    {
        this.txtIdCliente.clear();
        this.txtNombre.clear();
        
         this.rdbHombre.setSelected(false);
         this.rdbMujer.setSelected(false);
         this.rdbOtro.setSelected(true);
        
        this.limpiaChecks();
        
        this.laFoto="sinfoto.png";
        this.poneFoto();
        
        this.txtIdCliente.requestFocus();
        this.idActual="new";
        
    }
    
     @FXML
    private void doSeleccionarFoto(ActionEvent event) 
    {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.setInitialDirectory(new File("./Images/"));
        
        //Obtener la imagen seleccionada
        File imgFile = fileChooser.showOpenDialog(new Stage());
        this.laFoto=imgFile.getName();
        
        //Mostrar la imagen
        if(this.laFoto != null){
            Image image = new Image ("file:" + imgFile.getAbsolutePath());
            this.img_Foto.setImage(image);
        }
        
    }

    @FXML
    private void doGuardarModificar(ActionEvent event)
    {
        //modificar verificaciones
        String nombre, id;
        char genero='*';
        boolean productos[]={false,false,false,false,false};
        String errores="";
        int cont=0;
        boolean existe;
        
        nombre=this.txtNombre.getText();
        if (nombre.isEmpty())
            errores+="El campo NOMBRE está vacio\n";
        
        id=this.txtIdCliente.getText();
        if (id.isEmpty())
            errores+="El campo ID está vacio\n";
        
        
        if(this.rdbHombre.isSelected() )
        {
            genero='H';
        }
        if(this.rdbMujer.isSelected() )
        {
            genero='M';
        }
        if(this.rdbOtro.isSelected() )
        {
            genero='O';
        }
        
        
        if(this.chbxCuentaAhorros.isSelected() )
        {
            productos[0]=true;
        }
        if(this.chbxCuentaCorriente.isSelected() )
        {
            productos[1]=true;
        }
        if(this.chbxCDT.isSelected() )
        {
            productos[2]=true;
        }
        if(this.chbxTarjetaVisa.isSelected() )
        {
            productos[3]=true;
        }
        if(this.chbxTarjetaAmerica.isSelected() )
        {
            productos[4]=true;
        }
        
        
        for (int i=0;i<productos.length;i++)
        {
            if(productos[i]==true)
                cont++;
        }
        if (cont==0)
            errores+="Debe elegir al menos un producto\n";
        
        
        if(errores.length()==0)
        {
            Cliente cliente=new Cliente(id, nombre,this.laFoto,genero,productos);
            if(this.idActual.equals("new"))//Guardar
            {
                if(this.gesCli.pruebaExistencia(id))
                    this.manageMessages(1, "Ese ID ya existe");
                else
                {
                    if(this.gesCli.guardaCliente(cliente)==true)
                    {
                       this.manageMessages(2, "Los datos del Cliente han sido guardados con éxito ");
                        this.traeClientes(); 
                    }
                    else
                    {
                       this.manageMessages(1, "¡Lo sentimos! Parece que hubo un FALLO guardando los datos del Cliente.\nIntente nuevamente más tarde");
                        
                    }
                        
                }  
            }
            else//Modificaciones
            {
                Iterator<Cliente> iter =this.clientes.iterator();
                int pos=-1;
                
                while(iter.hasNext())
                {
                    pos++;
                    if(iter.next().getIdCliente().equals(this.idActual))
                    {
                        this.clientes.add(pos,cliente);
                        break;
                    }
                }
                
                this.gesCli.recargaArchivo(this.clientes);
                this.manageMessages(2, "Los datos del Cliente han sido MODIFICADOS con éxito");
                this.traeClientes();
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
                    break;
                }
                
            }
            if(!existe)
                this.manageMessages(1, "NO están registrados los datos de ese cliente");
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
    private void doSalir(ActionEvent event)
    {
        Stage stage = (Stage) this.btn_Salir.getScene().getWindow();
        stage.close();
        System.exit(0);
    }




//======================================================================================
//AUXILIARES
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
        
        //Nombre && ID
        this.txtIdCliente.setText(cliente.getIdCliente());
        this.txtNombre.setText(cliente.getNombre());
    
        
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
    
    private void poneFoto()
    {
        File imgFile=new File(this.rutaImages+this.laFoto);
        String url=imgFile.toURI().toString();
        Image image =new Image (url, true);
        this.img_Foto.setImage(image);
    }
    
    
    private boolean manageMessages(int caso,String message)
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
        if(caso == 3){//Confirmación
            msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Petición Eliminación");
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

   

    
}
