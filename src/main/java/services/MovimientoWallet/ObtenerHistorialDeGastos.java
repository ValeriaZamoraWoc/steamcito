/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.MovimientoWallet;

import CRUDs.CRUDWallet;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerHistorialDeGastos {
    CRUDWallet c = new CRUDWallet();
    
    public List<String> obtenerHistorialGastos(String mail){
        return c.historialDeWallet(mail);
    }
}
