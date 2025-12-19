package models.Empresas;

import models.Usuarios.Desarrollador;
import java.util.ArrayList;
import java.util.List;
import models.Juegos.Juego;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Empresa {
    private int id_Empresa=0;
    private String nombreEmpresa;
    private ArrayList<Juego> catalogo;
    private ArrayList<Desarrollador> equipo;

    public Empresa(String nombreEmpresa, Desarrollador miembro){
        this.nombreEmpresa= nombreEmpresa;
        catalogo= new ArrayList<>();
        equipo= new ArrayList<>();
        equipo.add(miembro);
    }

    public Empresa(String nombreEmpresa){
        this.nombreEmpresa = nombreEmpresa;
        catalogo = new ArrayList<>();
        equipo = new ArrayList<>();
    }

    public void agregarIdEmpresa(int id){
        if(this.id_Empresa != 0){
            return;
        }
        if(id <= 0){
            return;
        }
        this.id_Empresa = id;
    }

    
    public void agregarJuegoCatalogo(Juego juego){
        catalogo.add(juego);
    }

    public boolean agregarUsuarioEquipo(Desarrollador usuario){
        if(equipo.contains(usuario)){
            return false;   
        }
        equipo.add(usuario);
        return true;
    }


    public String getNombreEmpresa(){
        return nombreEmpresa;        
    }

    public List<Juego> getCatalogo(){
        return catalogo;
    }
}
