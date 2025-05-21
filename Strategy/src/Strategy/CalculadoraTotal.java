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
public class CalculadoraTotal {
    private EstrategiaCalculoTotal estrategia;

    public void setEstrategia(EstrategiaCalculoTotal estrategia) {
        this.estrategia = estrategia;
    }

    public double calcular(DefaultTableModel modeloResumen) {
        return estrategia.calcular(modeloResumen);
    }
}