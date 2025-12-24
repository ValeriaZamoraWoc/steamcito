/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Usuarios;


/**
 *
 * @author cacerola
 */
public class dtoUsuarioComun extends dtoUsuario {
    private int telefono;
    private String pais;


    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }


    public int getTelefono() {
        return telefono;
    }

    public String getPais() {
        return pais;
    }
}

