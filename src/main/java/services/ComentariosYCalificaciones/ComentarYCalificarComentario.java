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
public class ComentarYCalificarComentario {
    private CRUDComentario cCO= new CRUDComentario();
    private CRUDCalificacion cCA = new CRUDCalificacion();
    private CRUDUsuario cU = new CRUDUsuario();
    private CRUDJuego cJ = new CRUDJuego();
    
    
    public void comentarYCalificarComentario(String descripcion,String nombreJuego,String mail,Integer calificacion,
            Integer idComentarioPadre) {
        dtoUsuarioComun usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorTitulo(nombreJuego);
        dtoComentario comentarioPadre = cCO.buscarComentarioPadrePorId(idComentarioPadre);

        if (usuario == null || juego == null || comentarioPadre == null) {
            return;
        }

        // registrar respuesta
        cCO.registrarComentario(usuario,descripcion,comentarioPadre.getIdComentario(),juego);

        if (calificacion != null) {
            cCA.registrarCalificacion(comentarioPadre,usuario,juego.getId(),calificacion);
        }
    }
    
    public void comentarComentario(String descripcion, String nombreJuego, String mail){
        dtoUsuarioComun usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorTitulo(nombreJuego);
        
        cCO.registrarComentario(usuario, descripcion, null, juego);
    }
}
