/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesEstructurales;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
/**
 *
 * @author Michel Mendez
 */
public class Flyweight {
    public static class Categoria {
        private String nombre;

        public Categoria(String nombre) {
            this.nombre = nombre;
        }
        public String getNombre() {
            return nombre;
        }
    }

    public static class CategoriaFactory {
        private static final HashMap<String, Categoria> categorias = new HashMap<>();

        public static Categoria getCategoria(String nombre) {
            Categoria categoria = categorias.get(nombre);
            if (categoria == null) {
                categoria = new Categoria(nombre);
                categorias.put(nombre, categoria);
            }
            return categoria;
        }
    }
}
