package models.ObjetosUsuario;

import models.Juegos.Juego;
import java.util.ArrayList;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Biblioteca {

    private int id;
    private List<Juego> juegos;

    public Biblioteca(){
        this.juegos = new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public boolean agregarJuego(Juego juego){
        if(juego == null){
            return false;
        }
        if(juegos.contains(juego)){
            return false;
        }
        juegos.add(juego);
        return true;
    }

    public boolean contieneJuego(Juego juego){
        return juegos.contains(juego);
    }

    public List<Juego> getJuegos(){
        return List.copyOf(juegos);
    }
}
