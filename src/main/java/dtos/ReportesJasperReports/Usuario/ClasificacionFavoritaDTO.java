/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Usuario;

/**
 *
 * @author cacerola
 */
public class ClasificacionFavoritaDTO {

    private String nombreClasificacion;
    private int cantidad;

    public ClasificacionFavoritaDTO() {}

    public ClasificacionFavoritaDTO(String nombreClasificacion, int cantidad) {
        this.nombreClasificacion = nombreClasificacion;
        this.cantidad = cantidad;
    }

    public String getNombreClasificacion() {
        return nombreClasificacion;
    }

    public int getCantidad() {
        return cantidad;
    }
}
