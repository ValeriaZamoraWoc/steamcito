/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;

/**
 *
 * @author cacerola
 */
public class DesinstalarJuego {
    private CRUDJuego cJ = new CRUDJuego();
    private CRUDBiblioteca cB = new CRUDBiblioteca();
    private CRUDUsuario cU = new CRUDUsuario();
    
    public void desinstalarJuego(String mail, Integer idJuego){
        cB.desinstalarJuego(mail, idJuego);
    }
}
