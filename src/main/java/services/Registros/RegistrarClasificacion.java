/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDClasificacion;
import dtos.Juegos.dtoClasificacion;

/**
 *
 * @author cacerola
 */
public class RegistrarClasificacion {
    CRUDClasificacion crudClasificacion = new CRUDClasificacion();
    
    public void registrarClasificacion(String nombreClasificacion){
        dtoClasificacion clasificacion = new dtoClasificacion();
        
        clasificacion.setNombreCalsificacion(nombreClasificacion);
        crudClasificacion.registrarClasificacion(clasificacion);
    }
}
