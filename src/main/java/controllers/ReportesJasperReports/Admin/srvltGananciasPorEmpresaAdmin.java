/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.ReportesJasperReports.Admin;

import dtos.ReportesJasperReports.Admin.ReporteGananciasPorEmpresaAdminDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import services.ReportesAdminJasperReports.ReportesAdminJasperService;

/**
 *
 * @author cacerola
 */
@WebServlet("/admin/reporte/ganancias-por-empresa")
public class srvltGananciasPorEmpresaAdmin extends HttpServlet {

    private ReportesAdminJasperService service =
            new ReportesAdminJasperService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<ReporteGananciasPorEmpresaAdminDTO> datos =
                    service.gananciasPorEmpresa();

            InputStream jrxml = getServletContext().getResourceAsStream(
                    "/reportes/admin/GananciasPorEmpresaAdmin.jrxml"
            );

            if (jrxml == null) {
                throw new ServletException("No se encontró el archivo JRXML");
            }

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jrxml);

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(datos);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport,
                            null,
                            dataSource
                    );

            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=GananciasPorEmpresaAdmin.pdf"
            );

            JasperExportManager.exportReportToPdfStream(
                    jasperPrint,
                    response.getOutputStream()
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
