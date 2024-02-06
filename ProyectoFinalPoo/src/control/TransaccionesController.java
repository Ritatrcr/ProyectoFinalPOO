/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.GestionClientes;
import gestion.GestionProductos;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import negocio.Cliente;
import negocio.Producto;

/**
 * FXML Controller class
 *
 * @author ritatrindadedacruz
 */
public class TransaccionesController implements Initializable {

    @FXML
    private Button btnRetiro;
    @FXML
    private Button btnAvance;
    @FXML
    private Button btnSaldo;
    @FXML
    private Button btnPago;
    @FXML
    private Button btnDeposito;
    @FXML
    private Button btnCompra;
    @FXML
    private Button btnCambioClave;
    @FXML
    private Button btnSalir;
    @FXML
    private ImageView imgAsistente;
    @FXML
    private ImageView imgTarjeta;
    @FXML
    private ImageView imgRetiro;
    @FXML
    private ImageView imgAvance;
    @FXML
    private ImageView imgSaldo;
    @FXML
    private ImageView imgPago;
    @FXML
    private ImageView imgDeposito;
    @FXML
    private ImageView imgCompra;
    @FXML
    private ImageView imgCambioClave;
    @FXML
    private ImageView imgSalir;
    
    private GestionProductos gesPro;
    private GestionClientes gesCli;
    private ClienteController cliCon;
    private ProductosController1 proCon;
    private ArrayList<Producto> productos;
    private ArrayList<Cliente> clientes;
    private Cliente clienteActual;
    private Producto productoActual;
    private int cantidadProductos;
    
    
    @FXML
    private ComboBox<String> cbxProductos;
    @FXML
    private Label nombreCliente;
    @FXML
    private Label idCliente;
    @FXML
    private Label idCliente1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
        this.poneFoto();
        
        this.gesCli=new GestionClientes();
        this.gesPro=new GestionProductos();
        this.cliCon=new ClienteController();
        this.proCon=new ProductosController1();
        
        this.btnAvance.setDisable(true);
        this.btnSaldo.setDisable(true);
        this.btnCompra.setDisable(true);
        this.btnDeposito.setDisable(true);
        this.btnPago.setDisable(true);
        this.btnRetiro.setDisable(true);
        
        clientes = this.gesCli.getTodos();
        
    } 
    @FXML
    private void doValidacionProductosCreados(MouseEvent event) 
    {
        if(this.cantidadProductos==0)
        {
            this.cliCon.manageMessages(1,"Cree sus productos para poder hacer transacciones!\n¡Vuelva pronto!");
        }
    }
    
    @FXML
    private void doDefineProducto(ActionEvent event) 
    {
        {
            if((this.cbxProductos.getValue().equals("Cuenta Ahorros"))||(this.cbxProductos.getValue().equals("Cuenta Corriente")))
            {
                this.btnRetiro.setDisable(false);
                this.btnSaldo.setDisable(false);
                this.btnDeposito.setDisable(false);

                //deshabilitados
                this.btnAvance.setDisable(true);
                this.btnCompra.setDisable(true);
                this.btnPago.setDisable(true);
            }
            if((this.cbxProductos.getValue().equals("Tarjeta Visa"))||(this.cbxProductos.getValue().equals("Tarjeta American")))
            {
                this.btnAvance.setDisable(false);
                this.btnSaldo.setDisable(false);
                this.btnPago.setDisable(false);
                this.btnCompra.setDisable(false);

                //deshabilitados
                this.btnRetiro.setDisable(true);
                this.btnDeposito.setDisable(true);

            }
            if(this.cbxProductos.getValue().equals("CDT"))
            {
                //falta
                
                this.btnAvance.setDisable(true);
                this.btnSaldo.setDisable(false);
                this.btnCompra.setDisable(true);
                this.btnDeposito.setDisable(true);
                this.btnPago.setDisable(true);
                this.btnRetiro.setDisable(true);
            }

            int tipoProducto=this.proCon.deStringInt(this.cbxProductos.getValue());
            productos=this.gesPro.getTodos();
            for (Producto producto: productos)
            {
                if((clienteActual.getIdCliente().equalsIgnoreCase(producto.getIdCliente())) &&(producto.getTipoProducto()==tipoProducto))
                {
                   this.productoActual=producto;
                   break;
                }
            }
            
        }
    }
    

    @FXML
    private void doRetiro(MouseEvent event) 
    {
        //valor a retirar debe ser menor o igual al saldo actual en miles
        String input= this.pedirValor("Ingresa el valor (en miles) que va a Retirar");
        try 
        {
            float retiro=Float.parseFloat(input);
            //valor mayor a 0
            if (retiro>0) 
            {
                if(retiro<=this.productoActual.getValor1())
                {
                    
                    this.productoActual.setValor1(this.productoActual.getValor1()-retiro);
                    this.gesPro.recargaArchivo(productos);
                    if(retiro>=1000)
                        this.cliCon.manageMessages(2,"Operación exitosa\nRetiró: "+ retiro/1000+ " millones de pesos!");
                    else
                      this.cliCon.manageMessages(2,"Operación exitosa\nRetiró: "+ retiro+ " mil pesos!");
             
                }
                else
                {
                   this.cliCon.manageMessages(1,"SALDO INSUFICIENTE. Operación Cancelada!"); 
                }
            }
            else
            {
               this.cliCon.manageMessages(1,"El valor que va a RETIRAR NO puede ser menor ni igual a 0");

            }
        } 
        catch (NumberFormatException e) 
        {
           this.cliCon.manageMessages(1,"Debe ingresar caracteres de tipo numerico");
        }
    }

    @FXML
    private void doAvance(MouseEvent event) {
        
        //verificacion que sea numeros y no letras 
        //en millones
        //no puedes ser mayor al saldo
        //valor mayor a 0
        
        //valor a retirar debe ser menor o igual al saldo actual en miles
        String input= this.pedirValor("Ingresa el valor (en millones) del Avance");
        try 
        {
            float avance=Float.parseFloat(input);
            //valor mayor a 0
            if (avance>0) 
            {
                if(avance<=this.productoActual.getValor1()/1000)
                {
                    this.productoActual.setValor1(this.productoActual.getValor1()-avance*1000);
                    this.cliCon.manageMessages(2,"Operación exitosa\nAvance: "+ avance+ " millones de pesos!");
                    this.gesPro.recargaArchivo(productos);
                }
                else
                {
                   this.cliCon.manageMessages(1,"SALDO INSUFICIENTE. Operación Cancelada!"); 
                }
            }
            else
            {
               this.cliCon.manageMessages(1,"El valor que va a RETIRAR NO puede ser menor ni igual a 0");

            }
        } 
        catch (NumberFormatException e) 
        {
           this.cliCon.manageMessages(1,"Debe ingresar caracteres de tipo numerico");
        }
       
    }

    @FXML
    private void doSaldo(MouseEvent event) 
    {
        //muestra numero producto
        //Disponible: muestra saldo actual (valor1)  
        
        String saldo;
           
        //para productos que no sean CDT
        if(!this.cbxProductos.getValue().equals("CDT"))
        {
           
            
            if (this.productoActual.getValor1()>999) 
            {
                saldo=(this.productoActual.getValor1()/1000)+" millones de pesos";
            }
            else
            {
                if (this.productoActual.getValor1()<1) 
                {
                    saldo=(this.productoActual.getValor1()*10)+" pesos";
                }
                else
                {
                  saldo=(this.productoActual.getValor1())+" mil pesos";  
                }
               
            }
         
        this.cliCon.manageMessages(2,"Id Producto: "+this.productoActual.getIdProducto()+"\nSaldo Disponible: "+saldo);

        }
        else
        {
          double m =12* (this.productoActual.getValor2()/12);
          saldo=this.productoActual.getValor1() * Math.pow((1 + 0.005 / (this.productoActual.getValor2()/12)), m)+" ";
          
          String milesOPesos="";
          String saldoCantidad="";
          if(this.productoActual.getValor1()<1000)
              milesOPesos+=" mil pesos";
          else
              milesOPesos=" millones de pesos";
 
          if(this.productoActual.getValor1() * Math.pow((1 + 0.005 / (this.productoActual.getValor2()/12)), m)<1000)
               saldoCantidad+=" mil pesos";
          else
              saldoCantidad=" millones de pesos";
          this.cliCon.manageMessages(2,"La tasa de interés actual del banco es: 5% "+"\n\nId Producto: "+this.productoActual.getIdProducto()+"\n\nPlazo: "+this.productoActual.getValor2()+" meses\n\nInversión inicial: "+this.productoActual.getValor1()+milesOPesos+"\n\nMonto al vencimiento:"+saldo+ saldoCantidad);  
        }
    }

    @FXML
    private void doPago(MouseEvent event) 
    {
        //valor a retirar debe ser menor o igual al saldo actual en miles
        String input= this.pedirValor("Ingresa el valor (en miles) del Pago");
        try 
        {
           float pago=Float.parseFloat(input);
            //valor mayor a 0
            if (pago>0) 
            {
                if(pago<=this.productoActual.getValor1())
                {
                    this.productoActual.setValor1(this.productoActual.getValor1()-pago);
                    this.gesPro.recargaArchivo(productos);
                    if(pago>=1000)
                        this.cliCon.manageMessages(2,"Operación exitosa\nValor del Pago: "+ pago/1000+ " millones de pesos!");
                    else
                      this.cliCon.manageMessages(2,"Operación exitosa\nValor del Pago: "+ pago+ " mil pesos!");
                }
                else
                {
                   this.cliCon.manageMessages(1,"SALDO INSUFICIENTE. Operación Cancelada!"); 
                }
            }
            else
            {
               this.cliCon.manageMessages(1,"El valor que va a RETIRAR NO puede ser menor ni igual a 0");

            }
        } 
        catch (NumberFormatException e) 
        {
           this.cliCon.manageMessages(1,"Debe ingresar caracteres de tipo numerico");
        }
    }

    @FXML
    private void doDeposito(MouseEvent event) 
    {
        //verificacion que sea numeros y no letras 
        //depositar en miles
        String input= this.pedirValor("Ingresa el valor (en miles) del Depósito");
        try 
        {
            float deposito=Float.parseFloat(input);
            //valor mayor a 0
            if (deposito>0) 
            {
                this.productoActual.setValor1(this.productoActual.getValor1()+deposito);
                this.gesPro.recargaArchivo(productos);
                if(deposito<1000)
                    this.cliCon.manageMessages(2,"Operación exitosa\nValor del Deposito: "+ deposito+ " mil pesos!");
                else
                     this.cliCon.manageMessages(2,"Operación exitosa\nValor del Deposito: "+ deposito/1000+ " millones de pesos!");      
            }
            else
            {
               this.cliCon.manageMessages(1,"El valor del DEPÓSITO NO puede ser menor ni igual a 0");
            }
        } 
        catch (NumberFormatException e) 
        {
           this.cliCon.manageMessages(1,"Debe ingresar caracteres de tipo numerico");
        }
       
        
    }

    @FXML
    private void doCompra(MouseEvent event) 
    {
        //verificacion que sea numeros y no letras 
        //valor en millones
        //valor mayor a 0
        
        String input= this.pedirValor("Ingresa el valor (en millones) de la Compra");
        try 
        {
            float compra=Float.parseFloat(input);
            
            //valor mayor a 0
            if (compra>0) 
            {
                if(compra<=this.productoActual.getValor1()/1000)
                {
                    this.productoActual.setValor1(this.productoActual.getValor1()-compra*1000);
                    this.cliCon.manageMessages(2,"Operación exitosa\nValor de la Compra: "+ compra+ " millones de pesos!");
                    this.gesPro.recargaArchivo(productos);
                }
                else
                {
                   this.cliCon.manageMessages(1,"SALDO INSUFICIENTE. Operación Cancelada!"); 
                }
            }
            else
            {
               this.cliCon.manageMessages(1,"El valor que va a RETIRAR NO puede ser menor ni igual a 0");

            }
        } 
        catch (NumberFormatException e) 
        {
           this.cliCon.manageMessages(1,"Debe ingresar caracteres de tipo numerico");
        } 
    }

    @FXML
    private void doCambioCalve(MouseEvent event) 
    {
        clientes = this.gesCli.getTodos();
        boolean existeClave = false;
        String nuevaClave = this.pedirValor("Digite una nueva clave");

        for (Cliente esteCliente : clientes) {
            if ((esteCliente.getClave().equalsIgnoreCase(nuevaClave)) && (!this.clienteActual.getIdCliente().equalsIgnoreCase(esteCliente.getIdCliente()))) {
                this.cliCon.manageMessages(1, "¡Lo sentimos! Esa clave ya existe");
                existeClave = true;
                break;
            }
        }
        
         if (!existeClave) 
         {
            boolean seguro = this.cliCon.manageMessages(3, "¿Está seguro de que quiere cambiar la contraseña de " + clienteActual.getClave() + " a " + nuevaClave + "?");

            if (seguro) 
            {
                Iterator<Cliente> iter = this.clientes.iterator();
                int pos = -1;
                while(iter.hasNext())
                {
                    pos++;
                    if(iter.next().getIdCliente().equals(clienteActual.getIdCliente()))
                    {
                        iter.remove();
                        Cliente cliente = new Cliente (clienteActual.getIdCliente(),clienteActual.getNombre(),clienteActual.getFoto(),clienteActual.getGenero(),clienteActual.getProductos(),nuevaClave);
                        this.clientes.add(pos,cliente);
                        break;
                    }
                }
                this.gesCli.recargaArchivo(this.clientes);
                clientes=this.gesCli.getTodos();
                this.cliCon.manageMessages(2, "Cambio de clave EXITOSO");
            } 
            else 
            {
                this.cliCon.manageMessages(2, "Cambio de clave CANCELADO");
            }
        }
    }

    @FXML
    private void doSalir(MouseEvent event)
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
            stage.setTitle("Bienvenido@");
            stage.show();

            Stage myStage = (Stage)this.btnSalir.getScene().getWindow();
            myStage.close();
        }
        catch (IOException ex)
        {
//           Logger.getLogger(AllClientesController.class.getName()).log(Level.SEVERE,null,ex);
        } 
    }
    
//AUXILIARES======================================================================================
    
    private void poneFoto()
    {
        Image avance = new Image("file:" +"././ImagesInterface/avance.jpeg");
        imgAvance.setImage(avance);
        
        Image retiro = new Image("file:" +"././ImagesInterface/retiro.jpeg");
        imgRetiro.setImage(retiro);
        
        Image saldo = new Image("file:" +"././ImagesInterface/saldo.jpeg");
        imgSaldo.setImage(saldo);
        
        Image pago = new Image("file:" +"././ImagesInterface/pago.jpeg");
        imgPago.setImage(pago);
        
        Image compra = new Image("file:" +"././ImagesInterface/compra.jpeg");
        imgCompra.setImage(compra);
        
        Image deposito = new Image("file:" +"././ImagesInterface/deposito.jpeg");
        imgDeposito.setImage(deposito);
        
        Image clave = new Image("file:" +"././ImagesInterface/Cambiocontrasena.jpeg");
        imgCambioClave.setImage(clave);
        
        Image salir = new Image("file:" +"././ImagesInterface/salir.jpeg");
        imgSalir.setImage(salir);
        
        Image asistente = new Image("file:" +"././ImagesInterface/mujercitapng.png");
        imgAsistente.setImage(asistente);
        
        Image tarjeta = new Image("file:" +"././ImagesInterface/tarjeta.jpeg");
        imgTarjeta.setImage(tarjeta);
    }
    
    
    
    public void defineCliente(Cliente cliente)
    {
        this.clienteActual=cliente;
        this.nombreCliente.setText(cliente.getNombre());
        this.idCliente.setText(cliente.getIdCliente());
        this.productos=this.gesPro.getTodos();
        this.cantidadProductos=0;
        
        for (Producto producto: productos)
        {
            if(producto.getIdCliente().equals(cliente.getIdCliente()))
            {
                this.cbxProductos.getItems().add(producto.getProducto());
                this.cantidadProductos+=1;      
            }
        }
    }
    private String pedirValor(String msg)
    {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Dialog");
        dialog.setHeaderText(msg);
        Optional<String> result = dialog.showAndWait();
        return result.get();
    }  
}
