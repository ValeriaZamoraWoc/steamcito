/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ComentariosYCalificaciones;

import CRUDs.CRUDJuego;
import dtos.Juegos.dtoComentario;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerComentariosPorJuego {
    CRUDJuego cJ= new CRUDJuego();
    
    public List<dtoComentario> obtenerComentariosPorJuego(Integer idJuego){
        return cJ.obtenerComentariosJuego(idJuego);
    }
}
