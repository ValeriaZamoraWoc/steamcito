/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDGrupoFamiliar;

/**
 *
 * @author cacerola
 */
public class SacarUsuarioGrupoFamiliar {
    CRUDGrupoFamiliar cGF = new CRUDGrupoFamiliar();
    
    public void sacarUsuarioDeGrupo(Integer idGrupo , String mail){
        cGF.eliminarPersonaDeGrupo(idGrupo, mail);
    }
    
}
