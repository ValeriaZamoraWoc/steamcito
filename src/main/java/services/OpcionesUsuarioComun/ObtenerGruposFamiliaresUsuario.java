/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDGrupoFamiliar;
import dtos.Usuarios.dtoGrupoFamiliar;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerGruposFamiliaresUsuario {
    CRUDGrupoFamiliar cGF= new CRUDGrupoFamiliar();
    
    public List<dtoGrupoFamiliar> obtenerGruposPorUsuario(String mail){
        return cGF.obtenerGruposPorUsuario(mail);
    }
}
