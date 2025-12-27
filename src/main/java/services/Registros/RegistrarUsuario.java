/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDEmpresa;
import CRUDs.CRUDUsuario;
import CRUDs.CRUDWallet;
import controllers.Registros.TipoUsuario;
import dtos.Empresas.dtoEmpresa;
import dtos.ObjetosUsuario.dtoWallet;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioComun;
import dtos.Usuarios.dtoUsuarioDesarrollador;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author cacerola
 */
public class RegistrarUsuario {
    
    CRUDUsuario crudUsuario = new CRUDUsuario();
    
    public void registrarAdministrador(String mail, String nick, String contraseña, LocalDate feechaNacimiento){
        dtoUsuario usuario = new dtoUsuario();
        usuario.setMail(mail);
        usuario.setNickname(nick);
        usuario.setContraseña(contraseña);
        usuario.setFechaNacimiento(feechaNacimiento);
        
        try {
            crudUsuario.registrarUsuario(usuario, TipoUsuario.Admin);
        } catch (SQLException ex) {
            System.getLogger(RegistrarUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void registrarDesarrollador(String mail, String nick, String contraseña, LocalDate feechaNacimiento, Integer idEmpresa){
        CRUDEmpresa crudEmpresa= new CRUDEmpresa();
        
        dtoEmpresa empresa = crudEmpresa.buscarEmpresaPorId(idEmpresa);
        
        dtoUsuarioDesarrollador usuario = new dtoUsuarioDesarrollador();
        usuario.setMail(mail);
        usuario.setNickname(nick);
        usuario.setContraseña(contraseña);
        usuario.setFechaNacimiento(feechaNacimiento);
        usuario.setIdEmpresa(empresa.getIdEmpresa());
        try {
            crudUsuario.registrarUsuario(usuario, TipoUsuario.Desarrollador);
        } catch (SQLException ex) {
            System.getLogger(RegistrarUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    public void registrarUsuarioComun(String mail, String nick, String contraseña, LocalDate feechaNacimiento, 
            String pais, int telefono){
        
        
        dtoUsuarioComun usuario = new dtoUsuarioComun();
        usuario.setMail(mail);
        usuario.setNickname(nick);
        usuario.setContraseña(contraseña);
        usuario.setFechaNacimiento(feechaNacimiento);
        usuario.setTelefono(telefono);
        usuario.setPais(pais);
        
        try {
            crudUsuario.registrarUsuario(usuario, TipoUsuario.Comun);
        } catch (SQLException ex) {
            System.getLogger(RegistrarUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
