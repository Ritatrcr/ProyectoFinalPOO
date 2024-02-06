package gestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import negocio.Cliente;


public class GestionClientes {
    
     private String rutaCliente;
    
     public GestionClientes()
     {
        this.rutaCliente = "./Archivos/misCliente.txt";
        this.verificArchivo();
     }
       
    private void verificArchivo(){
        try{
          File filex = new File (this.rutaCliente);
          if(!filex.exists())
          {
              filex.createNewFile();
          }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null, "¡Fallo buscando ruta de archivo!", "Verificación Ruta de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean guardaCliente(Cliente cliente)
    {
        boolean confirma=false;
        try
        {
            File file = new File(this.rutaCliente);
            FileWriter fr = new FileWriter(file, true);
            PrintWriter ps = new PrintWriter(fr);
            ps.println(cliente);
            ps.close();
            confirma=true;
       
         }
        catch(IOException ioe)
        {
            System.exit(1);
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return confirma;
        
    }
    
     public Cliente buscarCliente(String id)
     {
         Cliente cliente=null;
         FileReader file;
         BufferedReader br;
         String registro;
         
         try
        {
          file=new FileReader (this.rutaCliente);
          br=new BufferedReader(file);
          while((registro=br.readLine()) != null)
          {
              String[] campos=registro.split(",");
              if (campos[0].equals(id))
              {
                  boolean productos[]=new boolean[5];
                  for (int i=4;i<=8;i++)
                  {
                      productos[i-4]=Boolean.parseBoolean(campos[i]);
                  }
                  cliente=new Cliente(campos[0],campos[1],campos[2],campos[3].charAt(0),productos,campos[9]);
                  break;
              }
          }
        }
         
        catch(IOException ioe)
        {
            System.exit(1);
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
         
        return cliente;
                
    }
         
             
    public ArrayList<Cliente> getTodos() {
        FileReader file;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        String registro;
        BufferedReader br;

        try
        {
            file = new FileReader(this.rutaCliente);
            br = new BufferedReader(file);
            while((registro = br.readLine()) != null && registro.length() != 0) 
            {
                String [] campos = registro.split(",");
                
                boolean[] productos = new boolean[5];
                for (int i = 0; i < 5; i++) 
                {
                  productos[i]=Boolean.parseBoolean(campos[i+4]); 
    
                }
                Cliente cliente = new Cliente (campos[0], campos[1], campos[2], campos[3].charAt(0), productos,campos[9]);
                clientes.add(cliente);
            }
        }
        catch(IOException ioe)
       
        {
            JOptionPane.showMessageDialog(null,"¡Error al recolectar información!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return clientes;
    }
    
     public boolean pruebaExistencia(String id)
     {
         boolean existe=false;
         FileReader file;
         BufferedReader br;
         String registro;
         
        
        try{
            file = new FileReader(this.rutaCliente);
            br=new BufferedReader(file);
            while((registro = br.readLine()) != null && registro.length() != 0) 
            {
               String [] campos = registro.split(",");
               if (campos[0].equals(id))
               {
                  existe=true;
                  break;
               }  
            }
           
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "¡Problema leyendo el archivo de Clientes!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
 
    public void recargaArchivo(ArrayList<Cliente> misClientes){
        
        try{
            File file = new File(this.rutaCliente);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter ps = new PrintWriter(fr);
            
            for (Cliente cliente: misClientes)
            {
                ps.println(cliente);
            }
            ps.close();
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "¡Fallo recargando el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarCliente (String id)
    {
        ArrayList<Cliente> misClientes = this.getTodos();

        for (Cliente cliente:misClientes){
            if (id.equals(cliente.getIdCliente()))
            {

                misClientes.remove(cliente);
                break;
            }
        }
        this.recargaArchivo(misClientes);
        JOptionPane.showMessageDialog(null, "¡Eliminación Exitosa!"); 
    }
    
}
