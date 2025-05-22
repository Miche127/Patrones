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
import modelo.ModeloProducto;

public class ControladorProducto {

    private EstadoProducto estadoActual;

    // Interfaz interna del patrón State
    private interface EstadoProducto {
        void ejecutar();
    }

    // Método para cambiar y ejecutar el estado
    private void cambiarEstado(EstadoProducto nuevoEstado) {
        this.estadoActual = nuevoEstado;
        this.estadoActual.ejecutar();
    }

    // Aplicación del patrón State solo para MostrarProductos
    public void MostrarProductos(JTable tablaTotalProductos) {
        cambiarEstado(() -> {
            configuracion.Conexion objetoConexion = new configuracion.Conexion();
            ModeloProducto objetoProducto = new ModeloProducto();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("id");
            modelo.addColumn("nombresProd");
            modelo.addColumn("Precio");
            modelo.addColumn("Stock");

            tablaTotalProductos.setModel(modelo);

            String sql = "SELECT idproducto, nombre, precioProducto, stock FROM producto";

            try {
                Statement st = objetoConexion.estableceConexion().createStatement();
                ResultSet rs = st.executeQuery(sql);

                while (rs.next()) {
                    objetoProducto.setIdProducto(rs.getInt("idproducto"));
                    objetoProducto.setNombreProducto(rs.getString("nombre"));
                    objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                    objetoProducto.setStockProducto(rs.getInt("stock"));

                    modelo.addRow(new Object[]{
                        objetoProducto.getIdProducto(),
                        objetoProducto.getNombreProducto(),
                        objetoProducto.getPrecioProducto(),
                        objetoProducto.getStockProducto()
                    });
                }

                tablaTotalProductos.setModel(modelo);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al mostrar productos: " + e.toString());
            } finally {
                objetoConexion.cerrarConexion();
            }
        });
    }

    // Los demás métodos siguen igual

    public void AgregarProducto(JTextField nombres, JTextField precioProducto, JTextField stock) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        ModeloProducto objetoProducto = new ModeloProducto();

        String consulta = "INSERT INTO producto (nombre, precioProducto, stock) VALUES (?, ?, ?);";

        try {
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.parseDouble(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void Seleccionar(JTable totalProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {
        int fila = totalProducto.getSelectedRow();
        try {
            if (fila >= 0) {
                id.setText(totalProducto.getValueAt(fila, 0).toString());
                nombres.setText(totalProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(totalProducto.getValueAt(fila, 2).toString());
                stock.setText(totalProducto.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
        }
    }

    public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        ModeloProducto objetoProducto = new ModeloProducto();

        String consulta = "UPDATE producto SET nombre = ?, precioProducto = ?, stock = ? WHERE idproducto = ?;";

        try {
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.parseDouble(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            cs.setInt(4, objetoProducto.getIdProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se modificó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }

    public void limpiarCamposproductos(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock) {
        id.setText("");
        nombres.setText("");
        precioProducto.setText("");
        stock.setText("");
    }

    public void EliminarProductos(JTextField id) {
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        ModeloProducto objetoProducto = new ModeloProducto();

        String consulta = "DELETE FROM producto WHERE idproducto = ?;";

        try {
            objetoProducto.setIdProducto(Integer.parseInt(id.getText()));

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoProducto.getIdProducto());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se eliminó correctamente: " + e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
}
