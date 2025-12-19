package services.General;

import models.Usuarios.Administrador;
import models.Usuarios.UsuarioComun;
import models.Usuarios.Usuario;
import models.Usuarios.Desarrollador;
import java.time.LocalDate;
import models.Juegos.Juego;
import models.ObjetosUsuario.Wallet;


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
    private Juego bannerPrincipal;

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
    
    public void crearNuevoUsuario(String tipo, String mail, String nick, String contraseña, LocalDate fechaNacimiento, int telefono, String pais){
        Usuario usuario= null;
        if(tipo.equals("Admin")){
            usuario = new Administrador(mail, nick, contraseña, fechaNacimiento);
        }else if(tipo.equals("Desarrollador")){
            usuario = new Desarrollador(mail, nick, contraseña, fechaNacimiento);
        }else if(tipo.equals("Comun")){
            usuario = new UsuarioComun(mail, nick, contraseña, fechaNacimiento);
            UsuarioComun uc = (UsuarioComun) usuario;
            uc.agregarDatosExtras(new Wallet(), telefono, pais);
        }
       // bd.registrarUsuario(usuario);
    }

    public Juego getBanner(){
        return bannerPrincipal;
    }

    public void setbanner(Juego juego){
        bannerPrincipal= juego;
    }
}
