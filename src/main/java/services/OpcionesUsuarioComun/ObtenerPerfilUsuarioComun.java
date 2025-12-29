/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerPerfilUsuarioComun {
    CRUDUsuario c= new CRUDUsuario();
    CRUDBiblioteca cb= new CRUDBiblioteca();
    
    public dtoUsuario obtenerUsuarioComun(String mail){
        return c.buscarUsuarioComunPorMail(mail);
    }
    
    public List<dtoJuego> obtenerBibliotecaUsuario(String mail) {
        try {
            return c.obtenerBibliotecaUsuario(mail);
        } catch (SQLException ex) {
            System.getLogger(ObtenerPerfilUsuarioComun.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }
    
    public boolean obtenerVisibilidadBiblioteca(String mail){
        return cb.obtenerVisibilidadBiblioteca(mail);
    }
}
