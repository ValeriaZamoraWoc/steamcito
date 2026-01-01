/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Usuario;

/**
 *
 * @author cacerola
 */
public class PrestadoMejorCalificadoDTO {

    private int idJuego;
    private String nombreJuego;
    private double promedio;
    private String urlImagen;

    public PrestadoMejorCalificadoDTO() {}

    public PrestadoMejorCalificadoDTO(int idJuego, String nombreJuego, double promedio, String urlImagen) {
        this.idJuego = idJuego;
        this.nombreJuego = nombreJuego;
        this.promedio = promedio;
        this.urlImagen = urlImagen;
    }

    public int getIdJuego() {
        return idJuego;
    }

    public String getNombreJuego() {
        return nombreJuego;
    }

    public double getPromedio() {
        return promedio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }
}
