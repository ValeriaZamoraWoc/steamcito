/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoJuego;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerBibliotecaUsuario {
    private CRUDUsuario cU= new CRUDUsuario();
    
    public List<dtoJuego> obtenerBibliotecaUsuario(String mail){
        List<dtoJuego> biblitoteca= null;
        try {
            biblitoteca=  cU.obtenerBibliotecaUsuario(mail);
        } catch (SQLException ex) {
            System.getLogger(ObtenerBibliotecaUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return biblitoteca;
    }
}
