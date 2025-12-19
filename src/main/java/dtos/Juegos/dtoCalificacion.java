/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Juegos;

/**
 *
 * @author cacerola
 */
public class dtoCalificacion {
    private int idCalificacion;
    private int calificacion;
    
    public void setIdCalificacion(int id){
        this.idCalificacion= id;
    }
    
    public void setCalificacion(int calificacion){
        this.calificacion= calificacion;
    }
    
    public int getIdCalificacion(){
        return idCalificacion;
    }
    
    public int getCalificacion(){
        return calificacion;
    }
}
