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
import modelo.ModeloCliente;

/**
 *
 * @author Samuel
 */
public class ControladorCliente {

    private static ControladorCliente instancia;

    private ControladorCliente() {}

    public static ControladorCliente getInstancia() {
        if (instancia == null) {
            instancia = new ControladorCliente();
        }
        return instancia;
    }

    
    
    
    public void Mostrarclientes(JTable tablaTotalClientes){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetocliente = new modelo.ModeloCliente();
        
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql="";
        
        modelo.addColumn("id");
        modelo.addColumn("nombres");
        modelo.addColumn("appaterno");
        modelo.addColumn("apmaterno");
        
        tablaTotalClientes.setModel(modelo);
        
        sql = "select cliente.idcliente, cliente.nombres, cliente.appaterno,cliente.apmaterno from cliente";
        
        
        try{
            Statement  st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
               objetocliente.setIdCliente(rs.getInt("idcliente"));
               objetocliente.setNombres(rs.getString("nombres"));
               objetocliente.setApPaterno(rs.getString("appaterno"));
               objetocliente.setApMaterno(rs.getString("apmaterno"));

               
               modelo.addRow(new Object[]{objetocliente.getIdCliente(), objetocliente.getNombres(), objetocliente.getApPaterno(), objetocliente.getApMaterno()});
            
        
        }
            
            tablaTotalClientes.setModel(modelo);
        } catch (Exception e) {
           
            JOptionPane.showMessageDialog(null,"Error al mostrar usuarios," +e.toString());
            
        }
        finally {
            
            objetoConexion.cerrarConexion();
        }
        
        
    }
    
    public void AgregarCliente (JTextField nombres, JTextField appaterno, JTextField apmaterno){
    
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();
        
        String consulta = "insert into cliente (nombres,appaterno,apmaterno) values (?,?,?);" ;
        
        
        try {
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());

            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se guardó correctamente");

            
            
        }catch (Exception e) {
            
           JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());

            
        }
        finally {
            objetoConexion.cerrarConexion();
    }
        
    }
    
    public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombre, JTextField appaterno, JTextField apmaterno){
        
        int fila = totalcliente.getSelectedRow();
        try{
        
        if (fila >=0) {
        
            id.setText(totalcliente.getValueAt(fila, 0).toString());
            nombre.setText(totalcliente.getValueAt(fila, 0).toString());
            appaterno.setText(totalcliente.getValueAt(fila, 0).toString());
            apmaterno.setText(totalcliente.getValueAt(fila, 0).toString());
        
        }
        }catch (Exception e){
            
            JOptionPane.showConfirmDialog(null,"Error al seleccionar:"+ e.toString());
        
    }
    
    }
    
    public void ModificarCliente (JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();
        
        String consulta = "Update cliente SET cliente.nombres=?, cliente.appaterno =?, cliente.apmaterno =?, where cliente.idcliente =?;";
        
        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(nombres.getText());
            objetoCliente.setApMaterno(nombres.getText());
            objetoCliente.setNombres(nombres.getText());

 CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
         
         cs.setString(1, objetoCliente.getNombres());
         cs.setString(2, objetoCliente.getApPaterno());
         cs.setString(3, objetoCliente.getApMaterno());
         cs.setInt(4, objetoCliente.getIdCliente());
        
         cs.execute();
         JOptionPane.showMessageDialog(null, "se modifico!");
            
        }catch (Exception e){
    
            JOptionPane.showMessageDialog(null, "Error al modificar"+e.toString());
    }
    
     finally{
            objetoConexion.cerrarConexion();
}
}
    public void limpiarCamposClientes(JTextField id,JTextField nombres, JTextField appaterno, JTextField apmaterno){
        
        id.setText("");
                nombres.setText("");
                        appaterno.setText("");
                                apmaterno.setText("");

        
    }
    
    public void EliminarClientes(JTextField id){
        
        configuracion.Conexion objetoConexion = new configuracion.Conexion();
        modelo.ModeloCliente objetoCliente = new modelo.ModeloCliente();
        
        String consulta = "delete from cliente where cliente.idcliente=?;";
        
        try{
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setInt(1, objetoCliente.getIdCliente());
            
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
