/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Prestamos;

import CRUDs.CRUDPrestamo;
import dtos.ObjetosUsuario.dtoPrestamo;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class Prestamos {
    CRUDPrestamo c = new CRUDPrestamo();
    public void registrarPrestamo(String mailD, String mailP, Integer idJuego, LocalDate fecha){
        dtoPrestamo prestamo = new dtoPrestamo();
        prestamo.setMailDueno(mailD);
        prestamo.setMailPrestamista(mailP);
        prestamo.setIdJuego(idJuego);
        prestamo.setFechaPrestamo(fecha);
        prestamo.setFechaDevolucion(null);
        prestamo.setDevuelto(false);
        
        c.registrarPrestamo(prestamo);
    }
    
    public void devolverUnPrestamo(String mail){
        dtoPrestamo prestamo = c.prestamosActivosUsuario(mail).getFirst();
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamo.setDevuelto(true);
        c.devolverPrestamo(prestamo);
    }


    public boolean tienePrestamosActivos(String mail){
        List<dtoPrestamo> prestamosActivos = c.prestamosActivosUsuario(mail);
        if(prestamosActivos == null || prestamosActivos.isEmpty()){
            return false;
        }
        return true;
    }
}
