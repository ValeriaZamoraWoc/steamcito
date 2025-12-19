/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Empresas;

/**
 *
 * @author cacerola
 */
public class dtoComision {
    private int idComision;
    private int porcentajeComision;
    
    public void setIdComision(int id){
        this.idComision= id;
    }
    
    public int getIdComision(){
        return idComision;
    }
    
    public int getPorcentajeComision(){
        return porcentajeComision;
    } 
    
    public void setPorcentaje(int porcentaje){
        this.porcentajeComision= porcentaje;
    }
}
