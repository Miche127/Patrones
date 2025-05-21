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
import modelo.ClienteMemento;


/**
 *
 * @author Michel Mendez
 */
public class ControladorCliente {
    
    private final ClienteAdministrador caretaker = new ClienteAdministrador();
    private final ModeloCliente cliente = new ModeloCliente();
    
    
    private JTextField txtnombrecliente;
    private JTextField txtappaterno;
    private JTextField txtapmaterno;
    private JTextField idcliente;

    public void GuardarCliente(JTextField nombres, JTextField appaterno, JTextField apmaterno) {
    if (!nombres.getText().isEmpty() && !appaterno.getText().isEmpty() && !apmaterno.getText().isEmpty()) {
        ClienteMemento memento = cliente.saveToMemento();
        caretaker.guardarMemento(memento);
    } else {
        JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacíos.");
    }
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
            nombre.setText(totalcliente.getValueAt(fila, 1).toString());
            appaterno.setText(totalcliente.getValueAt(fila, 2).toString());
            apmaterno.setText(totalcliente.getValueAt(fila, 3).toString());
        
        }
        }catch (Exception e){
            
            JOptionPane.showConfirmDialog(null,"Error al seleccionar:"+ e.toString());
        
    }
    
    }
    
    public void ModificarCliente(JTextField idcliente, JTextField txtnombrecliente, JTextField txtappaterno, JTextField txtapmaterno) {
    configuracion.Conexion objetoConexion = new configuracion.Conexion();

    String consulta = "UPDATE cliente SET nombres=?, appaterno=?, apmaterno=? WHERE idcliente=?;";
    
    try {
        // Primero, guarda el estado actual antes de modificar
        cliente.setIdCliente(Integer.parseInt(idcliente.getText()));
        cliente.setNombres(txtnombrecliente.getText());
        cliente.setApPaterno(txtappaterno.getText());
        cliente.setApMaterno(txtapmaterno.getText());

        ClienteMemento memento = cliente.saveToMemento();
        caretaker.guardarMemento(memento);
        System.out.println("Estado guardado: " + memento.getNombres() + " " + memento.getApPaterno());
        //caretaker.guardarMemento(cliente.saveToMemento());
        //System.out.println("Estado guardado: " + cliente.getNombres() + " " + cliente.getApPaterno());

        CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
        cs.setString(1, cliente.getNombres());
        cs.setString(2, cliente.getApPaterno());
        cs.setString(3, cliente.getApMaterno());
        cs.setInt(4, cliente.getIdCliente());
        cs.execute();
        
        JOptionPane.showMessageDialog(null, "Se modificó correctamente.");
    
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al modificar: " + e.toString());
    } finally {
        objetoConexion.cerrarConexion();
    }
}

    public void deshacerModificacion() {
    ClienteMemento memento = caretaker.restaurarMemento();
    
    if (memento != null) {
        cliente.restoreFromMemento(memento);
        System.out.println("Estado restaurado: " + cliente.getNombres() + " " + cliente.getApPaterno());
        
        // Actualizar los campos del formulario con el estado restaurado
        idcliente.setText(String.valueOf(cliente.getIdCliente()));
        txtnombrecliente.setText(cliente.getNombres());
        txtappaterno.setText(cliente.getApPaterno());
        txtapmaterno.setText(cliente.getApMaterno());

        JOptionPane.showMessageDialog(null, "Modificación revertida.");
    } else {
        System.out.println("No hay cambios previos guardados.");
        JOptionPane.showMessageDialog(null, "No hay cambios para deshacer.");
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
