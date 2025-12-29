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
    
    public List<String[]> obtenerJuegosMejorCalificadosEmpresa(Integer idEmpresa){
        return c.obtenerJuegosMejorCalificadosEmpresa(idEmpresa);
    }
    
    public List<String[]> obtenerJuegosPeorCalificadosEmpresa(Integer idEmpresa){
        return c.obtenerJuegosPeorCalificadosEmpresa(idEmpresa);
    }
    
    public List<String[]> obtenerTop5(Integer idEmpresa) {
        List<String[]> todos = c.obtenerJuegosMejorCalificadosEmpresa(idEmpresa);
        List<String[]> top = new ArrayList<>();

        int limite = Math.min(todos.size(), 5);

        for (int i = 0; i < limite; i++) {
            top.add(todos.get(i));
        }

        return top;
    }
    
     public List<String[]> obtenerComentariosMejorCalificadosEmpresa(Integer idEmpresa){
         return c.obtenerComentariosMejorCalificadosEmpresa(idEmpresa);
     }
}
