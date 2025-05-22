/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import PatronesCreacionales.ConexionSingleton;
import Flyweight.Flyweight;
import Flyweight.Flyweight.Categoria;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Samuel
 */
public class ControladorProducto {

    public void MostrarProductos(JTable tablaTotalProductos) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();

        DefaultTableModel modelo = new DefaultTableModel();

        String sql = "";

        modelo.addColumn("id");
        modelo.addColumn("nombresProd");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        modelo.addColumn("Categoria"); 

        tablaTotalProductos.setModel(modelo);

        sql = "select producto.idproducto, producto.nombre, producto.precioProducto, producto.stock, producto.categoria from producto;";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                objetoProducto.setIdProducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));

                Flyweight.Categoria categoriaFly = Flyweight.CategoriaFactory.getCategoria(rs.getString("categoria"));
                objetoProducto.setCategoria(categoriaFly.getNombre());
            
                modelo.addRow(new Object[]{
                    objetoProducto.getIdProducto(), 
                    objetoProducto.getNombreProducto(), 
                    objetoProducto.getPrecioProducto(), 
                    objetoProducto.getStockProducto(), 
                    objetoProducto.getCategoria()});

            }

            tablaTotalProductos.setModel(modelo);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios," + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }

    }

    public void AgregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();

        String consulta = "insert into producto (nombre , precioProducto, stock, categoria) values (?,?,?,?);";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));

            // 🪶 Flyweight: Obtener la categoría seleccionada y usar la fábrica
            String categoriaNombre = cmbCategoria.getSelectedItem().toString();
            Flyweight.Categoria categoriaFly = Flyweight.CategoriaFactory.getCategoria(categoriaNombre);
            objetoProducto.setCategoria(categoriaFly.getNombre());

            CallableStatement cs = conn.prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            cs.setString(4, objetoProducto.getCategoria());


            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void Seleccionar(JTable totalProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {

        int fila = totalProducto.getSelectedRow();
        try {

            if (fila >= 0) {

                id.setText(totalProducto.getValueAt(fila, 0).toString());
                nombres.setText(totalProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(totalProducto.getValueAt(fila, 2).toString());
                stock.setText(totalProducto.getValueAt(fila, 3).toString());
                
                String categoria = totalProducto.getValueAt(fila, 4).toString();
                cmbCategoria.setSelectedItem(categoria); // Selecciona automáticamente la categoría

            }
        } catch (Exception e) {

            JOptionPane.showConfirmDialog(null, "Error al seleccionar:" + e.toString());

        }

    }

    public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();

        String consulta = "Update producto SET producto.nombre=?, producto.precioProducto =?, producto.stock = ?, producto.categoria = ? where producto.idproducto = ? ;";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));

            // Flyweight desde JComboBox
            String categoriaSeleccionada = cmbCategoria.getSelectedItem().toString();
            Flyweight.Categoria categoriaFly = Flyweight.CategoriaFactory.getCategoria(categoriaSeleccionada);
            objetoProducto.setCategoria(categoriaFly.getNombre());

            CallableStatement cs = conn.prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            cs.setString(4, objetoProducto.getCategoria());
            cs.setInt(5, objetoProducto.getIdProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "se modifico!");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error al modificar" + e.toString());
        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void limpiarCamposproductos(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JComboBox<String> cmbCategoria) {

        id.setText("");
        nombres.setText("");
        precioProducto.setText("");
        stock.setText("");
        cmbCategoria.setSelectedIndex(0);

    }

    public void EliminarProductos(JTextField id) {

        //configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloProducto objetoProducto = new modelo.ModeloProducto();

        String consulta = "delete from producto where producto.idproducto=?;";

        try {
            Connection conn = PatronesCreacionales.ConexionSingleton.getInstancia().getConexion();
            
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));

            CallableStatement cs = conn.prepareCall(consulta);

            cs.setInt(1, objetoProducto.getIdProducto());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se eliminó correctamente" + e.toString());

        } finally {
            PatronesCreacionales.ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

}
