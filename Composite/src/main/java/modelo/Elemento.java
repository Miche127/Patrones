/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import configuracion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Michel Mendez
 */

public class Elemento implements Componente {
    private int id;
    private String nombre;
    private double precio;

    public Elemento(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Elemento: " + nombre + " | Precio: " + precio);
    }

    @Override
    public double calcularPrecioTotal() {
        return precio;
    }

    // Método para obtener un producto desde la BD
    public static Elemento obtenerDesdeBD(int idProducto) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.estableceConexion();
        Elemento elemento = null;

        try {
            String sql = "SELECT idproducto, nombre, precioProducto FROM producto WHERE idproducto = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                elemento = new Elemento(rs.getInt("idproducto"), rs.getString("nombre"), rs.getDouble("precioProducto"));
            }
        } catch (Exception e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return elemento;
    }
}

