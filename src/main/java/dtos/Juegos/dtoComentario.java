/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.Juegos;

import models.Juegos.Calificacion;

/**
 *
 * @author cacerola
 */
public class dtoComentario {

    private int idComentario;
    private String contenido;
    private boolean esVisible;

    public int getIdComentario(){
        return idComentario;
    }

    public void setIdComentario(int id){
        this.idComentario = id;
    }

    public String getContenido(){
        return contenido;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public boolean isEsVisible(){
        return esVisible;
    }

    public void setEsVisible(boolean esVisible){
        this.esVisible = esVisible;
    }
}
