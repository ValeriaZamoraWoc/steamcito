/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.MovimientoWallet;

import CRUDs.CRUDWallet;
import dtos.ObjetosUsuario.dtoWallet;

/**
 *
 * @author cacerola
 */
public class ObtenerEstadoWallet {
    CRUDWallet cW = new CRUDWallet();
    
    public dtoWallet obtenerEstadoWallet(String mail){
        return cW.obtenerWallet(mail);
    }
}
