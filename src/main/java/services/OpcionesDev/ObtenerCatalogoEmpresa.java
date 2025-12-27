/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesDev;

import CRUDs.CRUDEmpresa;
import dtos.Juegos.dtoJuego;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerCatalogoEmpresa {
    CRUDEmpresa cE = new CRUDEmpresa();
    
    public List<dtoJuego> obtenerCatalogoEmpresa(Integer idEmpresa){
        return cE.obtenerCatalogoEmpresa(idEmpresa);
    }
}
