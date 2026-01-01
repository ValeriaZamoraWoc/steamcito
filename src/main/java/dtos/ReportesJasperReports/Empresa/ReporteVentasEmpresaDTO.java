/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Empresa;

/**
 *
 * @author cacerola
 */
public class ReporteVentasEmpresaDTO {

    private String nombreJuego;
    private double montoTotal;
    private Integer porcentajeComision;
    private double comision;
    private double gananciaNeta;

    public String getNombreJuego() {
        return nombreJuego;
    }

    public void setNombreJuego(String nombreJuego) {
        this.nombreJuego = nombreJuego;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Integer getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Integer porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getGananciaNeta() {
        return gananciaNeta;
    }

    public void setGananciaNeta(double gananciaNeta) {
        this.gananciaNeta = gananciaNeta;
    }
}
