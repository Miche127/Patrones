/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author Michel Mendez
 */

import configuracion.Conexion;
import modelo.Elemento;
import modelo.Grupo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControladorGrupo {

    // Método para crear un grupo en la base de datos
    public void crearGrupo(String nombre) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.estableceConexion();

        try {
            String sql = "INSERT INTO grupo (nombre) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("Grupo creado con éxito.");
        } catch (SQLException e) {
            System.out.println("Error al crear grupo: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
    }

    // Método para agregar un producto a un grupo en la BD
    public void agregarProductoAGrupo(int idGrupo, int idProducto) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.estableceConexion();

        try {
            String sql = "INSERT INTO grupo_producto (fkgrupo, fkproducto) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
            System.out.println("Producto agregado al grupo.");
        } catch (SQLException e) {
            System.out.println("Error al agregar producto al grupo: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
    }
}

