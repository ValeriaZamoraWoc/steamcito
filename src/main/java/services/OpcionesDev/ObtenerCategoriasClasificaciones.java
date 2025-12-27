/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.OpcionesDev;

import CRUDs.CRUDCategoria;
import CRUDs.CRUDClasificacion;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoClasificacion;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class ObtenerCategoriasClasificaciones {
    CRUDClasificacion c1= new CRUDClasificacion(); 
    CRUDCategoria c2 = new CRUDCategoria();
    
    public List<dtoCategoria> obtenerCategorias(){
        return c2.obtenerCategorias();
    }
    
    public List<dtoClasificacion> obtenerCalsificaciones(){
        return c1.obtenerClasificaciones();
    }
}
