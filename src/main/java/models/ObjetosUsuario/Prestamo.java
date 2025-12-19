package models.ObjetosUsuario;

import models.Juegos.Juego;
import models.Usuarios.UsuarioComun;
import java.time.LocalDate;



public class Prestamo {

    private int id;
    private UsuarioComun prestamista;
    private UsuarioComun dueno;
    private Juego juego;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(UsuarioComun prestamista, UsuarioComun dueno, Juego juego){
        
        if(prestamista == null || dueno == null || juego == null){
            throw new IllegalArgumentException("Datos de préstamo inválidos");
        }
        
        this.prestamista = prestamista;
        this.dueno = dueno;
        this.juego = juego;
        this.fechaPrestamo = LocalDate.now();
        this.devuelto = false;
    }

    public int getId(){
        return id;
    }

    public void asignarId(int id){
        if(this.id == 0){
            this.id = id;
        }
    }

    public boolean estaDevuelto(){
        return devuelto;
    }

    public LocalDate getFechaPrestamo(){
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion(){
        return fechaDevolucion;
    }

    public Juego getJuego(){
        return juego;
    }

    public boolean devolver(){
        if(devuelto){
            return false;
        }
        this.devuelto = true;
        this.fechaDevolucion = LocalDate.now();
        return true;
    }
}
