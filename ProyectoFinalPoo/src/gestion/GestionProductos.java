/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import negocio.Producto;


public class GestionProductos {
    
     private String rutaProductos;
    
     public GestionProductos()
     {
        this.rutaProductos = "./Archivos/misProductos.txt";
        this.verificArchivo();
     }
       
    private void verificArchivo(){
        try{
          File filex = new File (this.rutaProductos);
          if(!filex.exists())
          {
              filex.createNewFile();
          }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null, "¡Fallo buscando ruta de archivo de Productos!", "Verificación Ruta de Archivo", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean guardaProducto(Producto producto)
    {
        boolean confirma=false;

       
        
        try
        {
            File file = new File(this.rutaProductos);
            FileWriter fr = new FileWriter(file, true);
            PrintWriter ps = new PrintWriter(fr);
            ps.println(producto);
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
    
     public Producto buscarProducto(String idProducto)
     {
         Producto producto=null;
         FileReader file;
         BufferedReader br;
         String registro;
         
         try
        {
          file=new FileReader (this.rutaProductos);
          br=new BufferedReader(file);
          while((registro=br.readLine()) != null)
          {
              String[] campos=registro.split(",");
              if (campos[2].equals(idProducto))
              {
                  producto=new Producto(campos[0],Integer.parseInt(campos[1]),campos[2],campos[3],Float.parseFloat(campos[4]), Integer.parseInt(campos[5]));
                  break;
              }
          }
        }
         
        catch(IOException ioe)
        {
            System.exit(1);
            JOptionPane.showMessageDialog(null, "¡Ha ocurrido un error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
         
        return producto;
                
    }
         
             
    public ArrayList<Producto> getTodos() {
        FileReader file;
        ArrayList<Producto> productos = new ArrayList<Producto>();
        String registro;
        BufferedReader br;

        try
        {
            file = new FileReader(this.rutaProductos);
            br = new BufferedReader(file);
            while((registro = br.readLine()) != null && registro.length() != 0) 
            {
                String [] campos = registro.split(",");
                
                Producto producto=new Producto(campos[0],Integer.parseInt(campos[1]),campos[2],campos[3],Float.parseFloat(campos[4]), Integer.parseInt(campos[5]));
                productos.add(producto);
            }
        }
        catch(IOException ioe)
       
        {
            JOptionPane.showMessageDialog(null,"¡Error al recolectar información!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }
    
    
     public boolean pruebaExistencia(String id)
     {
         boolean existe=false;
         FileReader file;
         BufferedReader br;
         String registro;
         
        
        try{
            file = new FileReader(this.rutaProductos);
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
            JOptionPane.showMessageDialog(null, "¡Problema leyendo el archivo de Productos!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
 
    public void recargaArchivo(ArrayList<Producto> misProductos){
        
        try{
            File file = new File(this.rutaProductos);
            FileWriter fr = new FileWriter(file, false);
            PrintWriter ps = new PrintWriter(fr);
            
            for (Producto producto: misProductos)
            {
                ps.println(producto);
            }
            ps.close();
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null, "¡Fallo recargando el archivo!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void eliminarProducto (String id)
    {
        ArrayList<Producto> misProductos = this.getTodos();

        for (Producto producto:misProductos){
            if (id.equals(producto.getIdProducto()))
            {

                misProductos.remove(producto);
                break;
            }
        }
        this.recargaArchivo(misProductos);
        JOptionPane.showMessageDialog(null, "¡Eliminación Exitosa!");
       
    }
    
}