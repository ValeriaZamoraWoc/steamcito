package models.Usuarios;

import java.sql.Date;
import java.time.LocalDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Usuario {
    protected String mail=null;
    protected String nickname=null;
    protected String contraseña=null;
    protected LocalDate fechaNacimiento= null;

    public Usuario(){}

    public Usuario(String mail, String nickname, String contraseña,LocalDate fechaNacimiento){
        this.mail = mail;
        this.nickname= nickname;
        this.contraseña = contraseña;
        this.fechaNacimiento= fechaNacimiento;
    }

    public void cerrarSesion(){
        
    }

    public String getMail(){
        return mail;
    }

    public String getNick(){
        return nickname;
    }
    public String getContraseña(){
        return contraseña;
    }
    public LocalDate getFecha(){
        return fechaNacimiento;
    }
}
