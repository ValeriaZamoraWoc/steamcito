/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDCategoria;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import CRUDs.CRUDVenta;
import CRUDs.CRUDWallet;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoBiblioteca;
import dtos.ObjetosUsuario.dtoBibliotecaJuego;
import dtos.ObjetosUsuario.dtoWallet;
import dtos.Usuarios.dtoUsuarioComun;
import java.time.LocalDate;

/**
 *
 * @author cacerola
 */
public class RegistrarCompra {
    CRUDBiblioteca crudB = new CRUDBiblioteca();
    CRUDVenta crudV = new CRUDVenta();
    CRUDJuego crudJ = new CRUDJuego();
    CRUDWallet crudW = new CRUDWallet();
    CRUDUsuario crudU = new CRUDUsuario();
    CRUDCategoria crudC = new CRUDCategoria();
    
    public boolean registrarCompra(String nombreJuego, String mail) {

        // Buscar juego
        dtoJuego juego = crudJ.buscarJuegoPorTitulo(nombreJuego);
        if (juego == null) {
            return false;
        }

        // biblioteca
        dtoBiblioteca biblioteca = crudB.buscarBibliotecaPorMail(mail);
        if (biblioteca == null) {
            return false; 
        }

        // wallet
        dtoWallet wallet = crudW.obtenerWallet(mail);
        if (wallet == null) {
            return false;
        }

        // usuario
        dtoUsuarioComun usuario = crudU.buscarUsuarioComunPorMail(mail);
        if (usuario == null) {
            return false;
        }

        // edad
        dtoCategoria categoria = crudC.buscarCategoriaPorId(juego.getCategoria());
        if (categoria == null) {
            return false; 
        }

        int edadUsuario = calcularEdad(usuario.getFechaNacimiento());
        if (edadUsuario < categoria.getEdadCategoria()) {
            return false;
        }

        // Validar saldo
        if (wallet.getSaldo() < juego.getPrecio()) {
            return false; 
        }

        // Registrar compra
        crudB.registrarJuegoEnBiblioteca(juego, biblioteca);

        dtoBibliotecaJuego bj = new dtoBibliotecaJuego();
        bj.setMailBiblioteca(biblioteca.getMailUsuario());
        bj.setIdJuego(juego.getId());
        bj.setInstalado(false);

        crudV.registrarVenta(bj, juego.getPrecio());
        crudW.descontarSaldo(wallet, juego.getPrecio());

        return true;
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}

