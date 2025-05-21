/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptador;

import java.sql.Connection;

/**
 *
 * @author Michel Mendez
 */

public interface DBAdapter {
    Connection getConnection();
}