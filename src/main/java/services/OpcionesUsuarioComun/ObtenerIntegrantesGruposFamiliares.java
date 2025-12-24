/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDGrupoFamiliar;
import dtos.Usuarios.dtoUsuarioComun;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerIntegrantesGruposFamiliares {
    CRUDGrupoFamiliar c= new CRUDGrupoFamiliar();
    
    public List<dtoUsuarioComun> obtenerIntegrantesGrupo(int idGrupo) {
        return c.obtenerIntegrantesGrupo(idGrupo);
    }
}
