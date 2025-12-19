package models.Empresas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Comision {

    private int id;
    private int porcentaje;

    public Comision(int id, int porcentaje){
        if(porcentaje < 0 || porcentaje > 100){
            throw new IllegalArgumentException("Porcentaje inválido");
        }
        this.id = id;
        this.porcentaje = porcentaje;
    }

    public int getId(){
        return id;
    }

    public int getPorcentaje(){
        return porcentaje;
    }

    public boolean actualizarPorcentaje(int nuevoPorcentaje){
        if(nuevoPorcentaje < 0 || nuevoPorcentaje > 100){
            return false;
        }
        if(nuevoPorcentaje > this.porcentaje){
            return false;
        }
        this.porcentaje = nuevoPorcentaje;
        return true;
    }
}

