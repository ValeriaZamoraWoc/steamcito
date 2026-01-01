/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Usuario;

/**
 *
 * @author cacerola
 */
public class CalificacionPersonalDTO {

    private int idJuego;
    private String nombreJuego;
    private int calificacion;
    private String urlImagen;

    public CalificacionPersonalDTO() {}

    public CalificacionPersonalDTO(int idJuego, String nombreJuego, int calificacion, String urlImagen) {
        this.idJuego = idJuego;
        this.nombreJuego = nombreJuego;
        this.calificacion = calificacion;
        this.urlImagen = urlImagen;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
}
