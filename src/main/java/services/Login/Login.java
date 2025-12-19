/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Login;

import CRUDs.CRUDSistema;
import dtos.Usuarios.dtoUsuario;
import models.Usuarios.Usuario;

/**
 *
 * @author cacerola
 */
public class Login {
    
    private CRUDSistema crud = new CRUDSistema();

    public dtoUsuario login(String mail, String password) {

        if (mail == null || password == null) {
            throw new RuntimeException("Datos incompletos");
        }

        dtoUsuario usuario = crud.inicioSesion(mail, password);

        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuario;
    }
}
