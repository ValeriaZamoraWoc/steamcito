package models.Usuarios;

import models.ObjetosUsuario.Biblioteca;
import models.Usuarios.UsuarioComun;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class GrupoFamiliar {
    
    private int id_grupoFamiliar;
    private UsuarioComun[] integrante;

    public GrupoFamiliar(){
        integrante = new UsuarioComun[6];
    }

    public Biblioteca obtenerBibliotecaIntegrante(UsuarioComun integrante){
        if(verificarIngrante(integrante)){
            return integrante.getBiblioteca();
        }
        return null;
    }

    private boolean verificarIngrante(UsuarioComun usuario){
        for (int i = 0; i < integrante.length; i++) {
            if(usuario == integrante[i]){
                return true;
            }
        }
        return false;
    }

    public void agregarAHistorial(){}
}
