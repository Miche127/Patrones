package controlador;

import PatronesCreacionales.ConexionSingleton;
import PatronesEstructurales.Flyweight;
import PatronesEstructurales.Flyweight.Categoria;
import PatronesEstructurales.Flyweight.CategoriaFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ControladorProducto implements IControlador {

    public void MostrarProductos(JTable tablaTotalProductos) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        modelo.addColumn("Categoría");

        tablaTotalProductos.setModel(modelo);

        String sql = "SELECT idproducto, nombre, precioProducto, stock, categoria FROM producto;";

        try {
            Connection conn = ConexionSingleton.getInstancia().getConexion();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int    id       = rs.getInt("idproducto");
                String nombre   = rs.getString("nombre");
                double precio   = rs.getDouble("precioProducto");
                int    stock    = rs.getInt("stock");
                String catName  = rs.getString("categoria");

                // 🪶 Flyweight
                Categoria categoriaFly = CategoriaFactory.getCategoria(catName);

                modelo.addRow(new Object[]{
                    id, nombre, precio, stock, categoriaFly.getNombre()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al mostrar productos: " + e.toString(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void AgregarProducto(JTextField nombres,
                                JTextField precioProducto,
                                JTextField stock,
                                JComboBox<String> cmbCategoria) {

        String sql = "INSERT INTO producto "
                   + "(nombre, precioProducto, stock, categoria) "
                   + "VALUES (?,?,?,?);";

        try {
            Connection conn = ConexionSingleton.getInstancia().getConexion();

            String nombreText = nombres.getText();
            double precioVal  = Double.parseDouble(precioProducto.getText());
            int    stockVal   = Integer.parseInt(stock.getText());
            String catText    = cmbCategoria.getSelectedItem().toString();

            // 🪶 Flyweight
            Categoria categoriaFly = CategoriaFactory.getCategoria(catText);

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, nombreText);
            cs.setDouble(2, precioVal);
            cs.setInt(3, stockVal);
            cs.setString(4, categoriaFly.getNombre());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al guardar producto: " + e.toString(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void Seleccionar(JTable tabla,
                           JTextField id,
                           JTextField nombres,
                           JTextField precioProducto,
                           JTextField stock,
                           JComboBox<String> cmbCategoria) {

        int fila = tabla.getSelectedRow();
        if (fila < 0) return;

        id.setText( tabla.getValueAt(fila, 0).toString() );
        nombres.setText( tabla.getValueAt(fila, 1).toString() );
        precioProducto.setText( tabla.getValueAt(fila, 2).toString() );
        stock.setText( tabla.getValueAt(fila, 3).toString() );
        cmbCategoria.setSelectedItem( tabla.getValueAt(fila, 4).toString() );
    }

    public void ModificarProducto(JTextField id,
                                  JTextField nombres,
                                  JTextField precioProducto,
                                  JTextField stock,
                                  JComboBox<String> cmbCategoria) {

        String sql = "UPDATE producto SET "
                   + "nombre = ?, precioProducto = ?, stock = ?, categoria = ? "
                   + "WHERE idproducto = ?;";

        try {
            Connection conn = ConexionSingleton.getInstancia().getConexion();

            int    idVal      = Integer.parseInt(id.getText());
            String nombreText = nombres.getText();
            double precioVal  = Double.parseDouble(precioProducto.getText());
            int    stockVal   = Integer.parseInt(stock.getText());
            String catText    = cmbCategoria.getSelectedItem().toString();

            // 🪶 Flyweight
            Categoria categoriaFly = CategoriaFactory.getCategoria(catText);

            CallableStatement cs = conn.prepareCall(sql);
            cs.setString(1, nombreText);
            cs.setDouble(2, precioVal);
            cs.setInt(3, stockVal);
            cs.setString(4, categoriaFly.getNombre());
            cs.setInt(5, idVal);

            cs.execute();
            JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al modificar producto: " + e.toString(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void EliminarProductos(JTextField id) {
        String sql = "DELETE FROM producto WHERE idproducto = ?;";

        try {
            Connection conn = ConexionSingleton.getInstancia().getConexion();
            int idVal = Integer.parseInt(id.getText());

            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, idVal);
            cs.execute();

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null,
                "Error al eliminar producto: " + e.toString(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            ConexionSingleton.getInstancia().cerrarConexion();
        }
    }

    public void limpiarCamposproductos(JTextField id,
                                       JTextField nombres,
                                       JTextField precioProducto,
                                       JTextField stock,
                                       JComboBox<String> cmbCategoria) {
        id.setText("");
        nombres.setText("");
        precioProducto.setText("");
        stock.setText("");
        cmbCategoria.setSelectedIndex(0);
    }
}
