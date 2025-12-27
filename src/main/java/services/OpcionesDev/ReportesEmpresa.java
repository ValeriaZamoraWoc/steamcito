/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesDev;

import CRUDs.CRUDEmpresa;
import dtos.Juegos.dtoJuego;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ReportesEmpresa {
    CRUDEmpresa c = new CRUDEmpresa();
    
    public List<String[]> obtenerVentasEmpresa(Integer idEmpresa){
        return c.obtenerVentasEmpresa(idEmpresa);
    }
    
    public List<dtoJuego> obtenerJuegosMejorCalificadosEmpresa(Integer idEmpresa){
        return c.obtenerJuegosMejorCalificadosEmpresa(idEmpresa);
    }
    
    public List<dtoJuego> obtenerJuegosPeorCalificadosEmpresa(Integer idEmpresa){
        return c.obtenerJuegosPeorCalificadosEmpresa(idEmpresa);
    }
    
    public List<dtoJuego> obtenerTop5(Integer idEmpresa){
        List <dtoJuego> todos= c.obtenerJuegosMejorCalificadosEmpresa(idEmpresa);
        
        List<dtoJuego> top = new ArrayList();
        for (int i = 0; i < 5; i++) {
            top.add(todos.get(i));
        }
        return top;
        
    }
}
