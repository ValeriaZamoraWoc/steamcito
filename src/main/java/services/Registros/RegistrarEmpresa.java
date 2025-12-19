/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDComision;
import CRUDs.CRUDEmpresa;
import dtos.Empresas.dtoComision;
import dtos.Empresas.dtoEmpresa;

/**
 *
 * @author cacerola
 */
public class RegistrarEmpresa {
    CRUDEmpresa crudEmpresa = new CRUDEmpresa();
    CRUDComision crudComision = new CRUDComision();
    
    public void registrarEmpresa(String nombreEmpresa, String descripcion){
        dtoComision comision = crudComision.buscarComision(15);
        if (comision == null) {
            throw new RuntimeException("No existe la comisión base");
        }

        dtoEmpresa empresa = new dtoEmpresa();
        empresa.setNombreEmpresa(nombreEmpresa);
        empresa.setDescripcion(descripcion);
        
        crudEmpresa.registrarEmpresa(empresa);
        empresa= crudEmpresa.buscarEmpresaPorNombre(nombreEmpresa);
        crudEmpresa.asignarComisionEmpresa(empresa, comision);
    }
}
