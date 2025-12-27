/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Empresas;

/**
 *
 * @author cacerola
 */
public class dtoPeticionComision {
    private int porcentaje;
    private String empresa;
    private int idEmpresa;
    private EstadoPeticion estado;

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }



    public EstadoPeticion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPeticion estado) {
        this.estado = estado;
    }
    
}
