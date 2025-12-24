package services.General;

import java.time.LocalDate;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class SistemaCentral {
    
    private static final SistemaCentral instancia = new SistemaCentral();

    private ConectorBD bd= new ConectorBD();

    public static SistemaCentral getInstancia(){
        return instancia;
    }

   /* public Usuario iniciarSesion(String mail, String contraseña){
        Usuario u = bd.iniciarSesionSistema(mail, contraseña);

        if(u == null){
            //idk que poner acá, luego veo
        }

        return u;
    }*/
    
    
}
