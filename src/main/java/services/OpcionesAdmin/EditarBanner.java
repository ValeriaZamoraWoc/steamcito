/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesAdmin;

import CRUDs.CRUDJuego;
import CRUDs.CRUDSistema;
import dtos.Juegos.dtoJuego;

/**
 *
 * @author cacerola
 */
public class EditarBanner {
    CRUDSistema c = new CRUDSistema();
    CRUDJuego j= new CRUDJuego();
    
    public void editarBanner(Integer idJuegoNuevo){
        Integer idJuegoAntiguo = c.obteneriIdBanner();
        c.editarBanner(idJuegoAntiguo, idJuegoNuevo);
    }
    
    public dtoJuego obtenerBanner(){
        Integer idJuegoAntiguo = c.obteneriIdBanner();
        return j.buscarJuegoPorId(idJuegoAntiguo);
    }
}
