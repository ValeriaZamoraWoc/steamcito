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
    
    public String registrarCompra(String nombreJuego, String mail) {

        // Buscar juego
        dtoJuego juego = crudJ.buscarJuegoPorTitulo(nombreJuego);
        if (juego == null) {
            return "noexiste el juego";
        }
        System.out.println("id categoria juego "+ juego.getCategoria() );

        // biblioteca
        dtoBiblioteca biblioteca = crudB.buscarBibliotecaPorMail(mail);
        if (biblioteca == null) {
            return "no existe la biblioteca"; 
        }

        // wallet
        dtoWallet wallet = crudW.obtenerWallet(mail);
        if (wallet == null) {
            return "no existe la wallet";
        }

        // usuario
        dtoUsuarioComun usuario = crudU.buscarUsuarioComunPorMail(mail);
        if (usuario == null) {
            return "no existe el usuario común";
        }

        // edad
        dtoCategoria categoria = crudC.buscarCategoriaPorId(juego.getCategoria());
        if (categoria == null) {
            return "no existe la categoria"; 
        }

        int edadUsuario = calcularEdad(usuario.getFechaNacimiento());
        if (edadUsuario < categoria.getEdadCategoria()) {
            return "no cumple con la edad";
        }

        // Validar saldo
        if (wallet.getSaldo() < juego.getPrecio()) {
            return "no tiene saldo suficiente"; 
        }

        // Registrar compra
        crudB.registrarJuegoEnBiblioteca(juego, biblioteca);

        dtoBibliotecaJuego bj = new dtoBibliotecaJuego();
        bj.setMailBiblioteca(biblioteca.getMailUsuario());
        bj.setIdJuego(juego.getId());
        bj.setInstalado(false);

        crudV.registrarVenta(bj, juego.getPrecio());
        crudW.descontarSaldo(wallet, juego.getPrecio());

        return "sí se pudo";
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return 0;
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }
}

