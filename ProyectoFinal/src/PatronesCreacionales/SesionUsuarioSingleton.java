/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PatronesCreacionales;
//aplicacion del patron singleton
/**
 *
 * @author Michel Mendez
 */
public class SesionUsuarioSingleton {
    private static SesionUsuarioSingleton instancia;
    private String nombreUsuario;
    private String rango;
    private SesionUsuarioSingleton() {}

    public static SesionUsuarioSingleton getInstancia() {
        if (instancia == null) {
            instancia = new SesionUsuarioSingleton();
        }
        return instancia;
    }
    public void setUsuario(String nombreUsuario, String rango) {
        this.nombreUsuario = nombreUsuario;
        this.rango = rango;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public String getRango() {
        return rango;
    }
}
