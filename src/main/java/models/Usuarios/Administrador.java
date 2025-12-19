package models.Usuarios;

import java.time.LocalDate;
import models.Empresas.Empresa;
import models.Juegos.Juego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Administrador extends Usuario {

    public Administrador(String mail, String nickname, String contraseña, LocalDate fechaNacimiento) {
        super(mail, nickname, contraseña, fechaNacimiento);
    }

    public boolean puedeModificarComision(){
        return true;
    }

    public boolean puedeEditarCategorias(){
        return true;
    }
}
