/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Samuel
 */
public class ControlAlquiler {

    private static ControlAlquiler instancia;

    private ControlAlquiler() {}

    public static ControlAlquiler getInstancia() {
        if (instancia == null) {
            instancia = new ControlAlquiler();
        }
        return instancia;
    }

    
    public void BuscarProducto(JTextField nombreProducto, JTable tablaproducto){
      
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("Nombre");
        modelo.addColumn("PrecioProducto");
        modelo.addColumn("stock");
        
        tablaproducto.setModel(modelo);
        
        try{
        String consulta = "SELECT * FROM producto WHERE producto.nombre LIKE CONCAT('%', ? , '%');";
        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
        
        ps.setString(1, nombreProducto.getText());
        
        ResultSet rs = ps.executeQuery();
        
        
        
        while(rs.next()){
            
            objetoProducto.setIdProducto(rs.getInt("idproducto")); 
            objetoProducto.setNombreProducto(rs.getString("nombre")); 
            objetoProducto.setPrecioProducto(rs.getDouble("precioProducto")); 
            objetoProducto.setStockProducto(rs.getInt("stock")); 

            modelo.addRow(new Object[]{objetoProducto.getIdProducto(),objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});
            
        }   
        tablaproducto.setModel(modelo);
        
        }  catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());
            
             
        }
        finally {
            
            objetoConexion.cerrarConexion();
    }
    
        for (int column = 0; column < tablaproducto.getColumnCount(); column++) {
            
            Class <?> columClass = tablaproducto.getColumnClass(column);
            tablaproducto.setDefaultEditor(columClass, null);
            
        }
    
    }
    public void SeleccionarProductoAlquiler (JTable tablaProducto, JTextField id, JTextField nombre, JTextField precioProducto, JTextField stock, JTextField precioFinal) { 


        int fila  = tablaProducto.getSelectedRow();
        
        try {
            
           if(fila>=0) {
               
               id.setText(tablaProducto.getValueAt(fila, 0).toString());
               nombre.setText(tablaProducto.getValueAt(fila, 1).toString());
               precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
               stock.setText(tablaProducto.getValueAt(fila, 3).toString());
               precioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());

           } 
            
        }catch (Exception e){
        
            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());
        
    }
}

 public void BuscarCliente(JTextField nombreCliente, JTable tablaCliente){
      
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("ApPaterno");
        modelo.addColumn("ApMaterno");
        
        tablaCliente.setModel(modelo);
        
        try{
        String consulta = "SELECT * FROM cliente WHERE cliente.nombres LIKE CONCAT('%', ? , '%');";
        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
        
        ps.setString(1, nombreCliente.getText());
        
        ResultSet rs = ps.executeQuery();
        
        
        
        while(rs.next()){
            
            objetoCliente.setIdCliente(rs.getInt("idcliente")); 
            objetoCliente.setNombres(rs.getString("nombres")); 
            objetoCliente.setApPaterno(rs.getString("appaterno")); 
            objetoCliente.setApMaterno(rs.getString("apmaterno")); 

            modelo.addRow(new Object[]{objetoCliente.getIdCliente(),objetoCliente.getNombres(), objetoCliente.getApPaterno(), objetoCliente.getApMaterno()});
            
        }   
        tablaCliente.setModel(modelo);
        
        }  catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());
            
             
        }
        finally {
            
            objetoConexion.cerrarConexion();
    }
    
        for (int column = 0; column < tablaCliente.getColumnCount(); column++) {
            
            Class <?> columClass = tablaCliente.getColumnClass(column);
            tablaCliente.setDefaultEditor(columClass, null);
            
        }
    
    }
  public void SeleccionarClienteAlquiler (JTable tablaCliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) { 


        int fila  = tablaCliente.getSelectedRow();
        
        try {
            
           if(fila>=0) {
               
               id.setText(tablaCliente.getValueAt(fila, 0).toString());
               nombres.setText(tablaCliente.getValueAt(fila, 1).toString());
               appaterno.setText(tablaCliente.getValueAt(fila, 2).toString());
               apmaterno.setText(tablaCliente.getValueAt(fila, 3).toString());

           } 
            
        }catch (Exception e){
        
            JOptionPane.showMessageDialog(null, "error al mostrar" + e.toString());
        
    }
}   
 
 
  public void pasarproductosVentas(JTable tablaResumen, JTextField idproducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock){
      
      DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
      
      int stockDisponible = Integer.parseInt(stock.getText());
      
      String idProducto = idproducto.getText();
      
      for (int i = 0; i < modelo.getRowCount(); i++ ){
          
      String idExistente = (String) modelo.getValueAt(i, 0);
          
          
      if (idExistente.equals(idproducto)){
              JOptionPane.showMessageDialog(null, "El producto ya está registrado");
              return;        
          }
      }
      
      
      String nProducto = nombreProducto.getText();
      double precioUnitario = Double.parseDouble(precioProducto.getText());
      int cantidad = Integer.parseInt(cantidadVenta.getText());
      
      
      
      if (cantidad > stockDisponible){
          JOptionPane.showMessageDialog(null, "La cantidad de venta no puede ser mayor al atock disponible");
          return;
      }
      
      double subtotal = precioUnitario * cantidad;
      
      modelo.addRow(new Object[]{idProducto, nProducto, precioUnitario, cantidad, subtotal});
  }
  
  public void eliminarProductoSeleccionadoResumenVenta(JTable tablaResumen){
      
      
      try{
          
          DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
      
      int indiceSeleccionado = tablaResumen.getSelectedRow();
      
      if (indiceSeleccionado !=-1) {
      modelo.removeRow(indiceSeleccionado);
  }
      else {
          JOptionPane.showMessageDialog(null, "seleccione una fila para eliminar");
          
      }
      }catch (Exception e){
          
          JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
      }
      
      
  }
  
  public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar){
      
      
    DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
    double totalSubtotal = 0;
    double iva = 0.18;
    double totaliva = 0;
    
    DecimalFormat formato = new DecimalFormat("#.##");
    for (int i = 0; i < modelo.getRowCount(); i++){
        
        totalSubtotal = Double.parseDouble(formato.format(totalSubtotal+(double)modelo.getValueAt(i, 4)));
        totaliva = Double.parseDouble(formato.format(iva*totalSubtotal));
  }
      
    totalPagar.setText(String.valueOf(totalSubtotal));
    IVA.setText(String.valueOf(totaliva));
    
    
    
  }
  
  public void crearFactura(JTextField codcliente){
      
      configuracion.Conexion objetoconexion = new configuracion.Conexion();
      
      modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();
      String consulta = "insert into factura (fechaFactura, fkcliente) values (curdate(),?);";
      
      try {
          
          objetoCliente.setIdCliente(Integer.parseInt(codcliente.getText()));
          
          CallableStatement cs = objetoconexion.estableceConexion().prepareCall(consulta);
          cs.setInt(1, objetoCliente.getIdCliente());
          cs.execute();
          
          JOptionPane.showMessageDialog(null, "La factura se creó ");
      }catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "La factura no se creó "+ e.toString());

      }finally {
          objetoconexion.cerrarConexion();
      }
      
  }


  
  public void realizarVenta (JTable tablaResumenVenta){
      
      
      configuracion.Conexion objetoconexion = new configuracion.Conexion();
      
      String consultaDetalle = "insert into detalle (fkfactura, fkproducto, cantidad, precioVenta) values ((SELECT MAX(idfactura)from factura),?,?,?);";
      String consultaStock = "update producto set producto.stock = stock - ? where idproducto = ?;";  
 
      try {
           PreparedStatement psDetalle = objetoconexion.estableceConexion().prepareStatement(consultaDetalle);
           PreparedStatement psStock = objetoconexion.estableceConexion().prepareStatement(consultaStock);

           int filas = tablaResumenVenta.getRowCount();
           for (int i = 0; i <filas; i++) {
              int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
              int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
              double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());
              
              psDetalle.setInt(1, idProducto);
              psDetalle.setInt(2, cantidad);
              psDetalle.setDouble(3, precioVenta);
              psDetalle.execute();
              
              
              psStock.setInt(1, cantidad);
              psStock.setInt(2, idProducto);
              psStock.executeUpdate();               
          }
           
           JOptionPane.showMessageDialog(null, "Se realizó la Renta");
           
      }catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Error al realizar Renta"+ e.toString());

      }
      finally {
          objetoconexion.cerrarConexion();
      }
          
         }

  
  
  public void limpiarLuegoVenta(JTextField buscarCliente, JTable tablaCliente, JTextField buscarProducto, JTable tablaproducto,
                                JTextField selectIdCliente, JTextField selectNombreCliente, JTextField AppaternoCliente,
                                JTextField selectApmaternoCliente, JTextField selectIdProducto, JTextField selectecNombreProducto,
                                JTextField selectecPrecioProducto, JTextField selectStockProducto, JTextField precioVenta,
                                JTextField cantidadVenta, JTable tablaresumen, JLabel IVA, JLabel total){
      
      
      
      
      buscarCliente.setText("");
      buscarCliente.requestFocus();
      DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();
      modeloCliente.setRowCount(0);
      
      
      
      buscarProducto.setText("");
      DefaultTableModel modeloProducto = (DefaultTableModel) tablaproducto.getModel();
      modeloProducto.setRowCount(0);
      
      selectIdCliente.setText(" ");
      selectNombreCliente.setText(" ");
      selectApmaternoCliente.setText(" ");
      selectApmaternoCliente.setText(" ");
      
      
      
      selectIdProducto.setText(" ");
      selectecNombreProducto.setText(" ");
      selectecPrecioProducto.setText(" ");
      selectStockProducto.setText(" "); 
      
      
      precioVenta.setText("");
      precioVenta.setEnabled(false);
      
      
      cantidadVenta.setText("");
      
      DefaultTableModel modeloResumenVenta = (DefaultTableModel) tablaresumen.getModel();
      modeloResumenVenta.setRowCount(0);
      
      
      IVA.setText("----");
      total.setText("----");
  }
  
  public void MostrarUltimaFactura (JLabel ultimaFactura){
      configuracion.Conexion objetoConexion = new configuracion.Conexion();
      try {
          String consulta = "SELECT MAX(idfactura) as UltimaFactura from factura;";
          
          PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
          
          ResultSet rs = ps.executeQuery();
          
          if (rs.next()) {
              
              ultimaFactura.setText(String.valueOf(rs.getInt("UltimaFactura")));
              
          }
          
      } catch (Exception e) {
          JOptionPane.showMessageDialog(null, "Error al mostrar ñla ultima factura"+ e.toString());
      }
      
      finally{
          objetoConexion.cerrarConexion();
          
      }
      
  }
  
}


