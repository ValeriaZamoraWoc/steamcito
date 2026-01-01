/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Usuario;

/**
 *
 * @author cacerola
 */
public class PrestadoMasJugadoDTO {

    private String nombreJuego;
    private int diasTotales;
    private String urlImagen;

    public PrestadoMasJugadoDTO() {}

    public PrestadoMasJugadoDTO(String nombreJuego, int diasTotales, String urlImagen) {
        this.nombreJuego = nombreJuego;
        this.diasTotales = diasTotales;
        this.urlImagen = urlImagen;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public int getDiasTotales() {
        return diasTotales;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
}
