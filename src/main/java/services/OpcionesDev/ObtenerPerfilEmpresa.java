/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesDev;

import CRUDs.CRUDEmpresa;
import dtos.Empresas.dtoEmpresa;

/**
 *
 * @author cacerola
 */
public class ObtenerPerfilEmpresa {
    CRUDEmpresa c = new CRUDEmpresa();
    
    public dtoEmpresa obtenerPerfilEmpresa(Integer idEmpresa){
        return c.buscarEmpresaPorId(idEmpresa);
    }
}
