/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesUsuarioComun;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDGrupoFamiliar;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ReportesUsuarioComun {
    CRUDBiblioteca c = new CRUDBiblioteca();
    CRUDGrupoFamiliar cf= new CRUDGrupoFamiliar();
    
    public List<String[]> obtenerMejoresCalificadosBiblioteca(String mail){
        return c.obtenerMejoresCalificadosBiblioteca(mail);
    }
    public List<String[]> obtenerCalificacionesPersonales(String mail){
        return c.obtenerCalificacionesPersonales(mail);
    }
    public List<String[]> obtenerClasificacionesFavoritas(String mail){
        return c.obtenerClasificacionesFavoritas(mail);
    }
    
    public List<String[]> obtenerPrestadosMasJugados(String mail){
        return cf.obtenerPrestadosMasJugados(mail);
    }
    public List<String[]> obtenerMejoresCalificadosPrestados(String mail){
        return cf.obtenerMejoresCalificadosPrestados(mail);
    }
}
