package services.General;

import models.Empresas.Empresa;
import models.Juegos.Juego;
import models.Usuarios.UsuarioComun;
import models.Usuarios.Usuario;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Tienda {
    
    private static Tienda instancia;
    private SistemaCentral sistema;
    private Usuario usuarioActual;

    private Tienda() {
        sistema = SistemaCentral.getInstancia();
    }

    public void iniciarSesion(String mail, String contraseña){
        //this.usuarioActual = sistema.iniciarSesion(mail, contraseña);
    }

    public static Tienda getInstancia() {
        if(instancia == null) instancia = new Tienda();
        return instancia;
    }


    public void ingresarBusqueda(String prompt){

    }
    public void modificarBanner(Juego nuevoBanner){}

    public void mostrarBanner(){
        Juego banner = sistema.getBanner();
    }
    

    public void calcularRecomendacion(){}
    public void mostrarListado(String tipo){}
    public void mostrarDetallesJuego(Juego juego){}
    public void mostraUsuarioComun(UsuarioComun usuario){}
    public void mostrarEmpresa(Empresa empresa){}

}
