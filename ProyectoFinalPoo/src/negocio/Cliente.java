/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author ritatrindadedacruz
 */
public class Cliente 
{
    
    private String idCliente;
    private String nombre;
    private String foto;
    private char genero;
    private boolean productos[];//cuenta ahorros,cuenta corriente,cdt, tarjeta visa, tarjeta american
    private String clave;
    
    public Cliente(String idCliente, String nombre, String foto, char genero, boolean[] productos,String clave) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.foto = foto;
        this.genero = genero;
        this.productos = productos;
        this.clave = clave;
    }


    public String getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public char getGenero() {
        return genero;
    }

    public boolean[] getProductos() {
        return productos;
    }
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public void setProductos(boolean[] productos) {
        this.productos = productos;
    }
    

    @Override
    public String toString() 
    {
        String productos="";
        for (int i=0;i<this.productos.length;i++)
        {
            if (i!=this.productos.length-1)
                productos+=this.productos[i]+",";
            else
                productos+=this.productos[i];
        }
        return this.idCliente+","+ this.nombre+","+ this.foto+","+this.genero+","+productos+","+this.clave;
    }
  
    
}
