/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Strategy;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Michel Mendez
 */
public class CalculoIVAEstándar implements EstrategiaCalculoTotal {
    @Override
    public double calcular(DefaultTableModel modeloResumen) {
        double subtotal = 0;
        for (int i = 0; i < modeloResumen.getRowCount(); i++) {
            subtotal += (double) modeloResumen.getValueAt(i, 4);
        }
        return subtotal * 0.18;
    }
}