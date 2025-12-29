/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDGrupoFamiliar;
import CRUDs.CRUDUsuario;
import dtos.Usuarios.dtoGrupoFamiliar;
import dtos.Usuarios.dtoUsuarioComun;

/**
 *
 * @author cacerola
 */
public class RegistrarUsuarioEnGrupo {
    CRUDGrupoFamiliar crudGP = new CRUDGrupoFamiliar();
    CRUDUsuario crudU = new CRUDUsuario();
    
    public void registrarUsuarioEnGrupo(String mail, Integer id){
        dtoUsuarioComun usuario= crudU.buscarUsuarioComunPorMail(mail);
        crudGP.agregarPersonaAGrupoFamiliar(id, usuario);
    }
}
