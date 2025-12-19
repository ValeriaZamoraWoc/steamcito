package models.Juegos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Calificacion {

    private int id;
    private int valor;

    public Calificacion(int valor){
        if(valor < 1 || valor > 5){
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        this.valor = valor;
    }

    public int getValor(){
        return valor;
    }

    public int getId(){
        return id;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public boolean cambiarCalificacion(int nuevoValor){
        if(nuevoValor < 1 || nuevoValor > 5){
            return false;
        }
        this.valor = nuevoValor;
        return true;
    }
}