/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interprete;

/**
 *
 * @author Garaudy
 */
public class Analizador{
    public static Expresion parse(String input) {
        input = input.toLowerCase().trim();

        if (input.contains(" and ")) {
            String[] partes = input.split(" and ");
            return new And(parse(partes[0]), parse(partes[1]));
        } else if (input.contains(" or ")) {
            String[] partes = input.split(" or ");
            return new Or(parse(partes[0]), parse(partes[1]));
        } else if (input.contains("stock >")) {
            int valor = Integer.parseInt(input.replaceAll("[^0-9]", ""));
            return new StockMayor(valor);
        } else if (input.contains("precio <")) {
            double valor = Double.parseDouble(input.replaceAll("[^0-9.]", ""));
            return new PrecioMenor(valor);
        } else if (input.startsWith("nombre ")) {
            String texto = input.substring(7).trim();
            return new Nombre(texto);
        } else
            return new Nombre(input.trim());
        }
    }

