/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Usuarios;

import java.time.LocalDate;

/**
 *
 * @author cacerola
 */
public class dtoUsuario {
    protected String mail;
    protected String nickname;
    protected String contraseña;
    protected LocalDate fechaNacimiento;

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMail() {
        return mail;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContraseña() {
        return contraseña;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    
}
