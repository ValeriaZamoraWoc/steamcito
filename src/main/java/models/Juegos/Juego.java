package models.Juegos;

import java.time.LocalDate;
import models.Empresas.Empresa;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Juego {

    private int id;
    private String nombre;
    private String descripcion;
    private String especificaciones;
    private Clasificacion clasificacion;
    private Categoria categoria;
    private Empresa empresa;
    private int precio;
    private boolean enVenta;
    private LocalDate fechaLanzamiento;

    public Juego(String nombre, String descripcion, String especificaciones,
                 Clasificacion clasificacion, Categoria categoria,
                 Empresa empresa, int precio, LocalDate fechaLanzamiento){

        if(nombre == null || nombre.isBlank()){
            throw new IllegalArgumentException("El juego debe tener nombre");
        }
        if(precio < 0){
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.especificaciones = especificaciones;
        this.clasificacion = clasificacion;
        this.categoria = categoria;
        this.empresa = empresa;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
        this.enVenta = true;
    }

    // constructor liviano para listados
    public Juego(int id, String nombre, int precio){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId(){
        return id;
    }

    public String getNombre(){
        return nombre;
    }

    public int getPrecio(){
        return precio;
    }

    public boolean estaEnVenta(){
        return enVenta;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public void desactivarVenta(){
        this.enVenta = false;
    }

    public void activarVenta(){
        this.enVenta = true;
    }
    
    public void cambiarEstadoVenta(boolean b){
        this.enVenta =b;
    }
}
