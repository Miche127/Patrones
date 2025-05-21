/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import configuracion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Michel Mendez
 */

public class Grupo implements Componente {
    private int id;
    private String nombre;
    private List<Componente> componentes = new ArrayList<>();

    public Grupo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void agregarComponente(Componente componente) {
        componentes.add(componente);
    }

    public void eliminarComponente(Componente componente) {
        componentes.remove(componente);
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Grupo: " + nombre);
        for (Componente componente : componentes) {
            componente.mostrarInformacion();
        }
    }

    @Override
    public double calcularPrecioTotal() {
        double total = 0;
        for (Componente componente : componentes) {
            total += componente.calcularPrecioTotal();
        }
        return total;
    }

    // Obtener un grupo de productos desde la BD
    public static Grupo obtenerGrupoDesdeBD(int idGrupo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.estableceConexion();
        Grupo grupo = null;

        try {
            String sql = "SELECT idgrupo, nombre FROM grupo WHERE idgrupo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                grupo = new Grupo(rs.getInt("idgrupo"), rs.getString("nombre"));
                String sqlProductos = "SELECT fkproducto FROM grupo_producto WHERE fkgrupo = ?";
                PreparedStatement psProductos = conn.prepareStatement(sqlProductos);
                psProductos.setInt(1, idGrupo);
                ResultSet rsProductos = psProductos.executeQuery();

                while (rsProductos.next()) {
                    Elemento producto = Elemento.obtenerDesdeBD(rsProductos.getInt("fkproducto"));
                    if (producto != null) {
                        grupo.agregarComponente(producto);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener grupo: " + e.getMessage());
        } finally {
            conexion.cerrarConexion();
        }
        return grupo;
    }
}
