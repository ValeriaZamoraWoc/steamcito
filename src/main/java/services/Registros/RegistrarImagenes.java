/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDImagen;
import CRUDs.CRUDJuego;
import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoImagen;

/**
 *
 * @author cacerola
 */
public class RegistrarImagenes {
    CRUDImagen cI = new CRUDImagen();
    CRUDJuego cJ = new CRUDJuego();
    
    public void registrarImagenJuego(Integer idJuego, String url){
        dtoJuego juego = cJ.buscarJuegoPorId(idJuego);
        cI.registrarImagen(url);
        cI.actualizarImagenJuego(idJuego, url);
    }
}
