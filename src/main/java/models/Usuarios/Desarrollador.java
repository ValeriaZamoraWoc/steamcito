package models.Usuarios;

import java.time.LocalDate;
import models.Juegos.Categoria;
import models.Juegos.Clasificacion;
import models.Juegos.Comentario;
import models.Empresas.Empresa;
import models.Juegos.Juego;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class Desarrollador extends Usuario {

    private Empresa empresa;

    public Desarrollador(String mail, String nickname, String contraseña, LocalDate fechaNacimiento) {
        super(mail, nickname, contraseña, fechaNacimiento);
    }

    public Empresa getEmpresa(){
        return empresa;
    }

    public void setEmpresa(Empresa empresa){
        this.empresa = empresa;
    }

    public boolean puedePublicarJuego(){
        return empresa != null;
    }

    public void suspenderVenta(Juego juego){
        juego.cambiarEstadoVenta(false);
    }

    public void reanudarVenta(Juego juego){
        juego.cambiarEstadoVenta(true);
    }
}
