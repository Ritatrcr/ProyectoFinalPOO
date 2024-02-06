/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author ritatrindadedacruz
 */
public class Producto 
{
    private String idProducto;
    private int tipoProducto;
    private String idCliente;
    private String Fecha;
    private float valor1;
    private int valor2;
    private String producto;
    
    public Producto(String idProducto, int tipoProducto, String idCliente, String Fecha, float valor1, int valor2)
    {
        this.idProducto = idProducto;
        this.tipoProducto = tipoProducto;
        this.idCliente = idCliente;
        this.Fecha = Fecha;
        this.valor1 = valor1;
        this.valor2 = valor2;
        product(tipoProducto);
    }

    public String getIdProducto() {
        return idProducto;
    }

    public int getTipoProducto() {
        return tipoProducto;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getFecha() {
        return Fecha;
    }

    public float getValor1() {
        return valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setFecha(String Fecha) {
        this.Fecha = Fecha;
    }

    public void setValor1(float valor1) {
        this.valor1 = valor1;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }



    @Override
    public String toString() {
        return  this.idProducto + "," + this.tipoProducto + "," + this.idCliente + "," + this.Fecha + "," + this.valor1 + "," + this.valor2;
    }
    
    private void product(int n)
    {
        switch(n)
        {
            case 0:
                producto = "Cuenta Ahorros";
                break;
            case 1:
                producto = "Cuenta Corriente";
                break;
            case 2:
                producto = "CDT";
                break;
            case 3:
                producto = "Tarjeta Visa";
                break;
            case 4:
                producto = "Tarjeta American";
                break;
            default:
                producto = null;
                break;
        }
    }

    public String getProducto() 
    {
        return producto;
    }
    
    
}

