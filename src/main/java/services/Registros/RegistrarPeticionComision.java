/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDEmpresa;
import CRUDs.CRUDPeticiones;
import dtos.Empresas.dtoEmpresa;

/**
 *
 * @author cacerola
 */
public class RegistrarPeticionComision {
    CRUDPeticiones crudPC = new CRUDPeticiones();
    CRUDPeticiones cP= new CRUDPeticiones();
    CRUDEmpresa crudE= new CRUDEmpresa();
    
    public boolean registrarEmpresa(String nombreEmpresa, Integer porcentaje){
        dtoEmpresa empresa = crudE.buscarEmpresaPorNombre(nombreEmpresa);
        
        if(!cP.existePeticion(empresa.getIdEmpresa())){
            crudPC.registrarPeticion(empresa, porcentaje);
            return true;
        }
        return false;
    }
}
