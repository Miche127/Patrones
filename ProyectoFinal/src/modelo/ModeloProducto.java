    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import PatronesComportamiento.Prototype.Clonable;

/**
 *
 * @author Samuel
 */
public class ModeloProducto implements Clonable<ModeloProducto>{

    int idProducto;
    String nombreProducto;
    Double precioProducto;
    int stockProducto;
    String categoria;

    public ModeloProducto() {
        
    }
    
    public ModeloProducto(int idProducto, String nombreProducto, Double precioProducto, int stockProducto, String categoria) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
        this.categoria = categoria;
    }
    
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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
    
    @Override
    public ModeloProducto clonar() {
        return new ModeloProducto(idProducto, nombreProducto, precioProducto, stockProducto, categoria);
    }
}
