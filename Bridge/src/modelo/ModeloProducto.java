package modelo;

public class ModeloProducto implements Cloneable {

    public ModeloProducto(int aInt, String string, double aDouble, int aInt1) {
    }

    private int idProducto;
    private String nombreProducto;
    private Double precioProducto;
    private int stockProducto;

    // Getters y Setters
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

    // ----------------------------
    //      Método clone()
    // ----------------------------
    @Override
    public ModeloProducto clone() {
        ModeloProducto clon = null;
        try {
            // super.clone() hace una “copia superficial” (shallow copy)
            clon = (ModeloProducto) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clon;
    }
}
