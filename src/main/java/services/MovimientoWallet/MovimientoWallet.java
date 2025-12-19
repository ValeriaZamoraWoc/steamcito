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
public class MovimientoWallet {
    CRUDWallet crud = new CRUDWallet();
    
    public void agregarSaldo(String mail, Integer monto){
        dtoWallet wallet = crud.obtenerWallet(mail);
        
        crud.recargarSaldo(wallet, monto);
    }
    
    public void descontarSaldo(String mail, Integer monto){
        dtoWallet wallet = crud.obtenerWallet(mail);
        
        crud.descontarSaldo(wallet, monto);
    }
}
