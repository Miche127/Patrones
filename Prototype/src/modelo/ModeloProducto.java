/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Samuel
 */
public class ModeloProducto implements Prototype<ModeloProducto>{

    int idProducto;
    String nombreProducto;
    Double precioProducto;
    int stockProducto;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }
    
    @Override
    public ModeloProducto clonar() {
        ModeloProducto clon = new ModeloProducto();
        clon.setIdProducto(this.idProducto);
        clon.setNombreProducto(this.nombreProducto);
        clon.setPrecioProducto(this.precioProducto);
        clon.setStockProducto(this.stockProducto);
        return clon;
    }

}
