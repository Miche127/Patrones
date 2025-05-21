/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author Samuel
 */
public class ControladorProducto {
    
     public void MostrarProductos(JTable tablaTotalProductos){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("nombresProd");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        
        tablaTotalProductos.setModel(modelo);
        
        sql = "select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock from producto;";
        
        
        try{
            Statement  st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
               objetoProducto.setIdProducto(rs.getInt("idproducto"));
               objetoProducto.setNombreProducto(rs.getString("nombre"));
               objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
               objetoProducto.setStockProducto(rs.getInt("stock"));

               
               modelo.addRow(new Object[]{objetoProducto.getIdProducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});
            
        
        }
            
            tablaTotalProductos.setModel(modelo);
        } catch (Exception e) {
           
            JOptionPane.showMessageDialog(null,"Error al mostrar usuarios," +e.toString());
            
        }
        finally {
            
            objetoConexion.cerrarConexion();
        }
        
        
    }
   
     
      public void AgregarProducto (JTextField nombres, JTextField precioProducto, JTextField stock){
    
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
        String consulta = "insert into producto (nombre , precioProducto, stock) values (?,?,?);" ;
        
        
        try {
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

            
            
        }catch (Exception e) {
            
           JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());

            
        }
        finally {
            objetoConexion.cerrarConexion();
    }
      }   
        
         public void Seleccionar (JTable totalProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock){
        
        int fila = totalProducto.getSelectedRow();
        try{
        
        if (fila >=0) {
        
            id.setText(totalProducto.getValueAt(fila, 0).toString());
            nombres.setText(totalProducto.getValueAt(fila, 0).toString());
            precioProducto.setText(totalProducto.getValueAt(fila, 0).toString());
            stock.setText(totalProducto.getValueAt(fila, 0).toString());
        
        }
        }catch (Exception e){
            
            JOptionPane.showConfirmDialog(null,"Error al seleccionar:"+ e.toString());
        
    }
    
    }
       
         public void ModificarProducto (JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
        String consulta = "Update producto SET producto.nombre=?, producto.precioProducto =?, producto.stock = ? where producto.idproducto = ? ;";
        
        try {
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            
 CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
         
         cs.setString(1, objetoProducto.getNombreProducto());
         cs.setDouble(2, objetoProducto.getPrecioProducto());
         cs.setInt(3, objetoProducto.getStockProducto());
         cs.setInt(4, objetoProducto.getIdProducto());
        
         cs.execute();
         JOptionPane.showMessageDialog(null, "se modifico!");
            
        }catch (Exception e){
    
            JOptionPane.showMessageDialog(null, "Error al modificar"+e.toString());
    }
    
     finally{
            objetoConexion.cerrarConexion();
}
}
     
          public void limpiarCamposproductos(JTextField id,JTextField nombres, JTextField precioProducto, JTextField stock){
        
        id.setText("");
                nombres.setText("");
                        precioProducto.setText("");
                                stock.setText("");

        
    }
         
           public void EliminarProductos(JTextField id){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();
        
        String consulta = "delete from producto where producto.idproducto=?;";
        
        try{
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setInt(1, objetoProducto.getIdProducto());
            
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
            
        } catch (Exception e){
            
           JOptionPane.showMessageDialog(null, "No se eliminó correctamente"+ e.toString());

        }
        finally {
           objetoConexion.cerrarConexion();
        }
    }
         
    }
    