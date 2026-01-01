/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Usuario;

import java.time.LocalDate;

/**
 *
 * @author cacerola
 */
public class HistorialGastoDTO {

    private int numero;
    private String nombreJuego;
    private LocalDate fecha;
    private int precio;

    public HistorialGastoDTO() {}

    public HistorialGastoDTO(int numero, String nombreJuego, LocalDate fecha, int precio) {
        this.numero = numero;
        this.nombreJuego = nombreJuego;
        this.fecha = fecha;
        this.precio = precio;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getPrecio() {
        return precio;
    }
}
