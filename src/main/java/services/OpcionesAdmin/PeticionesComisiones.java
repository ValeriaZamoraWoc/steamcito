/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesAdmin;

import CRUDs.CRUDComision;
import CRUDs.CRUDEmpresa;
import CRUDs.CRUDPeticiones;
import dtos.Empresas.dtoComision;
import dtos.Empresas.dtoEmpresa;
import dtos.Empresas.dtoPeticionComision;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class PeticionesComisiones {
    CRUDPeticiones c = new CRUDPeticiones();
    CRUDComision o= new CRUDComision();
    CRUDEmpresa e = new CRUDEmpresa();
    
    public List<dtoPeticionComision> obtenerPeticiones(){
        return c.obtenerPeticiones();
    }
    public void aceptarPeticion(Integer idEmpresa){
        c.aceptarPeticion(idEmpresa);
        dtoPeticionComision pc= c.obtenerPeticion(idEmpresa);
        o.registrarComisionConPorcentaje(pc.getPorcentaje());
        dtoComision comi = o.buscarComision(pc.getPorcentaje());
        o.modificarComisionEmpresa(pc.getIdEmpresa(), comi.getIdComision());
        
    }
    public void rechazarPeticion(Integer idEmpresa){
        c.rechazarPeticion(idEmpresa);
    }
}
