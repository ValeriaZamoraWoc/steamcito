/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ComentariosYCalificaciones;

import CRUDs.CRUDCalificacion;
import CRUDs.CRUDComentario;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoComentario;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuarioComun;

/**
 *
 * @author cacerola
 */
public class ComentarYCalificarJuego {
    private CRUDComentario cCO= new CRUDComentario();
    private CRUDCalificacion cCA = new CRUDCalificacion();
    private CRUDUsuario cU = new CRUDUsuario();
    private CRUDJuego cJ = new CRUDJuego();
    
    
    public void comentarYCalificarJuego(String descripcion, String nombreJuego, String mail, Integer calificacion){
        dtoUsuarioComun usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorTitulo(nombreJuego);
        
        if (usuario == null || juego == null) {
            return;
        }
        
        cCO.registrarComentario(usuario, descripcion, null, juego);
        dtoComentario comentario = cCO.buscarComentarioPadre(usuario);
        
        if(comentario != null){
            cCA.registrarCalificacion(comentario, usuario, juego.getId(), calificacion);
        }
    }
    
    public void comentarJuego(String descripcion, String nombreJuego, String mail){
        dtoUsuarioComun usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorTitulo(nombreJuego);
        
        cCO.registrarComentario(usuario, descripcion, null, juego);
    }
}
