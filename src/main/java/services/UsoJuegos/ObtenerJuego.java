/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDJuego;
import dtos.Juegos.dtoJuego;

/**
 *
 * @author cacerola
 */
public class ObtenerJuego {
    CRUDJuego cJ = new CRUDJuego();
    
    public dtoJuego obtenerJuego(Integer id){
        return cJ.buscarJuegoPorId(id);
    }
}
