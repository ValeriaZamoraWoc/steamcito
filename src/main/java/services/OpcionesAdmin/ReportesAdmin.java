/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesAdmin;

import CRUDs.CRUDSistema;
import dtos.Juegos.dtoJuego;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ReportesAdmin {
    CRUDSistema c = new CRUDSistema();
    
    public List<String[]> gananciasGlobales(){
        return c.gananciasGlobales();
    }
    
    public List<String[]> gananciasPorEmpresa(){
        return c.gananciasPorEmpresa();
    }
    
    public List<String[]> jugadoresConMasJuegos() {
        return c.jugadoresConMasJuegos();
    }
    
    public List<String[]> juegosMasVendidosClasificacion(String clasificacion){
        return c.juegosMasVendidosClasificacion(clasificacion);
    }
    
    public List<String[]> juegosMasVendidosCategoria(String categoria){
        return c.juegosMasVendidosCategoria(categoria);
    }
    
    public List<String[]> mejoresCalificadosClasificacion(String clasificacion) {
        return c.mejoresCalificadosClasificacion(clasificacion);
    }
    
    public List<String[]> mejoresCalificadosCategoria(String categoria){
        return c.mejoresCalificadosCategoria(categoria);
    }
}
