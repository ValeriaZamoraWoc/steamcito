package models.Juegos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Comentario {

    private int id;
    private String contenido;
    private boolean visible;

    public Comentario(String contenido){
        if(contenido == null || contenido.isBlank()){
            throw new IllegalArgumentException("No hay contenido");
        }
        this.contenido = contenido;
        this.visible = true;
    }

    public int getId(){
        return id;
    }

    public String getContenido(){
        return contenido;
    }

    public boolean esVisible(){
        return visible;
    }

    public void ocultar(){
        this.visible = false;
    }

    public void mostrar(){
        this.visible = true;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public boolean editarContenido(String nuevoContenido){
        if(nuevoContenido == null || nuevoContenido.isBlank()){
            return false;
        }
        this.contenido = nuevoContenido;
        return true;
    }
}
