/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.ReportesJasperReports.Empresa;

import dtos.Empresas.dtoEmpresa;
import dtos.ReportesJasperReports.Empresa.ReporteComentariosEmpresaDTO;
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
import services.ReportesAdminJasperReports.ReportesEmpresaJasperService;

/**
 *
 * @author cacerola
 */
@WebServlet("/empresa/reporte/comentarios-mejor-calificados")
public class srvltComentariosMejorCalificadosEmpresa extends HttpServlet {

    private ReportesEmpresaJasperService service =
            new ReportesEmpresaJasperService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idEmpresaStr = request.getParameter("idEmpresa");

        if (idEmpresaStr == null || idEmpresaStr.isBlank()) {
            throw new ServletException("Falta el parámetro idEmpresa");
        }

        Integer idEmpresa = Integer.parseInt(idEmpresaStr);

        try {
            dtoEmpresa empresa = service.buscarEmpresaPorId(idEmpresa);
            List<ReporteComentariosEmpresaDTO> datos =
                    service.obtenerComentariosMejorCalificadosEmpresa(idEmpresa);

            InputStream jrxml =
                    getServletContext().getResourceAsStream(
                        "/reportes/empresa/ComentariosMejorCalificadosEmpresa.jrxml"
                    );

            if (jrxml == null) {
                throw new ServletException("No se encontró el archivo JRXML");
            }

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(jrxml);

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(datos);

            Map<String, Object> params = new HashMap<>();
            params.put("NOMBRE_EMPRESA", empresa.getNombreEmpresa());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                        jasperReport,
                        params,
                        dataSource
                    );

            response.setContentType("application/pdf");
            response.setHeader(
                "Content-Disposition",
                "inline; filename=ComentariosMejorCalificadosEmpresa.pdf"
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
