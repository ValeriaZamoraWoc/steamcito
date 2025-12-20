/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDBiblioteca;
import dtos.ObjetosUsuario.dtoBiblioteca;

/**
 *
 * @author cacerola
 */
public class CambiarVisibilidadBiblioteca {
    CRUDBiblioteca cB = new CRUDBiblioteca();
    
    public void cambiarVisibilidadBiblioteca(String mail){
        dtoBiblioteca biblioteca = cB.buscarBibliotecaPorMail(mail);
        
        cB.cambiarVisibilidadBiblioteca(biblioteca);
    }
}
