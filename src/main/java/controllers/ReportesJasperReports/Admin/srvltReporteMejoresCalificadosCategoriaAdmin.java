/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.ReportesJasperReports.Admin;

import dtos.ReportesJasperReports.Admin.ReporteMejoresCalificadosAdminDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@WebServlet("/admin/reporte/mejores-calificados/categoria")
public class srvltReporteMejoresCalificadosCategoriaAdmin extends HttpServlet {

    private ReportesAdminJasperService service =
            new ReportesAdminJasperService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String categoria = request.getParameter("categoria");
        if (categoria == null || categoria.isBlank()) {
            throw new ServletException("Falta el parámetro categoria");
        }

        try {
            List<ReporteMejoresCalificadosAdminDTO> datos =
                    service.mejoresCalificadosCategoria(categoria);

            InputStream jrxml =
                    getServletContext().getResourceAsStream(
                        "/reportes/admin/MejoresCalificadosCategoriaAdmin.jrxml"
                    );

            if (jrxml == null) {
                throw new ServletException("No se encontró el archivo JRXML");
            }

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jrxml);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("CATEGORIA", categoria);

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(datos);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        dataSource
                    );

            response.setContentType("application/pdf");
            response.setHeader(
                "Content-Disposition",
                "inline; filename=MejoresCalificadosCategoriaAdmin.pdf"
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
