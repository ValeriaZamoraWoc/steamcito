/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDEmpresa;
import CRUDs.CRUDPeticionComision;
import dtos.Empresas.dtoEmpresa;

/**
 *
 * @author cacerola
 */
public class RegistrarPeticionComision {
    CRUDPeticionComision crudPC = new CRUDPeticionComision();
    CRUDEmpresa crudE= new CRUDEmpresa();
    
    public void registrarEmpresa(String nombreEmpresa, Integer porcentaje){
        dtoEmpresa empresa = crudE.buscarEmpresaPorNombre(nombreEmpresa);
        
        crudPC.registrarPeticion(empresa, porcentaje);
    }
}
