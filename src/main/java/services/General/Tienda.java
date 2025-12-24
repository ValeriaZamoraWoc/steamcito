package services.General;

import CRUDs.CRUDSistema;
import dtos.Juegos.dtoJuego;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Tienda {
    private CRUDSistema cS= new CRUDSistema();
    
    public List<dtoJuego> obtenerTodosLosJuegos(){
        return cS.obtenerTodosLosJuegos();
    }

}
