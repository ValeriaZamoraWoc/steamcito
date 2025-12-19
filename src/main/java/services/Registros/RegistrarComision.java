/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDComision;
import dtos.Empresas.dtoComision;

/**
 *
 * @author cacerola
 */
public class RegistrarComision {
    CRUDComision crudComision = new CRUDComision();
    
    public void registrarComision(Integer porcentaje){
        dtoComision comision = new dtoComision();
        comision.setPorcentaje(porcentaje);
        
        crudComision.registrarComision(comision);
    }
}
