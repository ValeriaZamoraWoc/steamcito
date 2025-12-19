package models.Usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import models.ObjetosUsuario.Biblioteca;
import models.Juegos.Juego;
import models.ObjetosUsuario.Prestamo;
import models.ObjetosUsuario.Wallet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class UsuarioComun extends Usuario {

    private Wallet wallet;
    private int telefono;
    private String pais;
    private Biblioteca biblioteca;
    private ArrayList<Prestamo> prestamos = new ArrayList<>();

    public UsuarioComun(String mail, String nickname, String contraseña, LocalDate fechaNacimiento) {
        super(mail, nickname, contraseña, fechaNacimiento);
        this.biblioteca = new Biblioteca();
    }

    public void agregarDatosExtras(Wallet wallet, int telefono, String pais){
        this.wallet = wallet;
        this.telefono = telefono;
        this.pais = pais;
    }

    public boolean puedeComprar(Juego juego){
        return wallet != null && wallet.getSaldo() >= juego.getPrecio();
    }

    public void agregarJuegoABiblioteca(Juego juego){
        biblioteca.agregarJuego(juego);
    }

    public Wallet getWallet(){
        return wallet;
    }

    public Biblioteca getBiblioteca(){
        return biblioteca;
    }
}
