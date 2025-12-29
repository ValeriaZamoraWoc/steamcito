/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ComentariosYCalificaciones;

import CRUDs.CRUDComentario;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoComentario;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioDesarrollador;

/**
 *
 * @author cacerola
 */
public class DesabilitarComentarios {
    private CRUDComentario cC= new CRUDComentario();
    private CRUDUsuario cU= new CRUDUsuario();
    
    public String desabilitarComentario(Integer idComentario){
        dtoComentario comentario = cC.buscarComentarioPorId(idComentario);
        
        if(comentario ==null){
            return "Comentario no existe";
        }
        
        cC.ocultarComentario(comentario);
        return "Comentario oculto";
    }
}
