/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDJuego;
import CRUDs.CRUDVenta;
import CRUDs.CRUDWallet;
import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoBiblioteca;
import dtos.ObjetosUsuario.dtoBibliotecaJuego;
import dtos.ObjetosUsuario.dtoWallet;

/**
 *
 * @author cacerola
 */
public class RegistrarCompra {
    CRUDBiblioteca crudB = new CRUDBiblioteca();
    CRUDVenta crudV = new CRUDVenta();
    CRUDJuego crudJ = new CRUDJuego();
    CRUDWallet crudW = new CRUDWallet();
    
    public boolean registrarCompra(String nombreJuego, String mail) {

        dtoJuego juego = crudJ.buscarJuegoPorTitulo(nombreJuego);
        if (juego == null) {
            return false; // juego no existe
        }

        dtoBiblioteca biblioteca = crudB.buscarBibliotecaPorMail(mail);
        if (biblioteca == null) {
            return false; // biblioteca no existe
        }

        dtoWallet wallet = crudW.obtenerWallet(mail);
        if (wallet == null) {
            return false; // wallet no existe
        }

        if (wallet.getSaldo() < juego.getPrecio()) {
            return false; // saldo insuficiente
        }

        crudB.registrarJuegoEnBiblioteca(juego, biblioteca);

        dtoBibliotecaJuego bj = new dtoBibliotecaJuego();
        bj.setMailBiblioteca(biblioteca.getMailUsuario());
        bj.setIdJuego(juego.getId());
        bj.setInstalado(false);

        crudV.registrarVenta(bj, juego.getPrecio());
        crudW.descontarSaldo(wallet, juego.getPrecio());

        return true;
    }

}
