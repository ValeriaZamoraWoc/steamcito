/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Empresas;


/**
 *
 * @author cacerola
 */
public class dtoEmpresa {
    private int idEmpresa=0;
    private String nombreEmpresa;
    private String descripcion;
    
    public void setIdEmpresa(int id){
        this.idEmpresa= id;
    }
    
    public void setNombreEmpresa(String nombre){
        this.nombreEmpresa= nombre;
    }
    
    public int getIdEmpresa(){
        return idEmpresa;
    }
    
    public String getNombreEmpresa(){
        return nombreEmpresa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
