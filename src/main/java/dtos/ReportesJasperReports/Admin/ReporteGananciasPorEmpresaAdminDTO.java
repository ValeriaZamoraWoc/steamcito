/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos.ReportesJasperReports.Admin;

/**
 *
 * @author cacerola
 */
public class ReporteGananciasPorEmpresaAdminDTO {

    private String nombreEmpresa;
    private Double totalGanancia;

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Double getTotalGanancia() {
        return totalGanancia;
    }

    public void setTotalGanancia(Double totalGanancia) {
        this.totalGanancia = totalGanancia;
    }
}
