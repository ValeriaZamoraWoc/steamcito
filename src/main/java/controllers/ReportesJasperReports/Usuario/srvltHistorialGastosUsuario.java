/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.ReportesJasperReports.Usuario;
import dtos.ReportesJasperReports.Usuario.HistorialGastoDTO;
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
import services.ReportesAdminJasperReports.ReportesUsuarioJasperReports;
/**
 *
 * @author cacerola
 */
@WebServlet("/usuario/reporte/historial-gastos")
public class srvltHistorialGastosUsuario extends HttpServlet {

    private ReportesUsuarioJasperReports service =
            new ReportesUsuarioJasperReports();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mail = request.getParameter("mail");

        if (mail == null || mail.isBlank()) {
            throw new ServletException("Falta el parámetro mail");
        }

        try {
            List<HistorialGastoDTO> datos =
                    service.obtenerHistorialGastos(mail);

            InputStream jrxml =
                    getServletContext().getResourceAsStream(
                        "/reportes/usuario/HistorialGastosUsuario.jrxml"
                    );

            if (jrxml == null) {
                throw new ServletException("No se encontró el archivo JRXML");
            }

            JasperReport report =
                    JasperCompileManager.compileReport(jrxml);

            JRBeanCollectionDataSource ds =
                    new JRBeanCollectionDataSource(datos);

            Map<String, Object> params = new HashMap<>();
            params.put("MAIL_USUARIO", mail);

            JasperPrint print =
                    JasperFillManager.fillReport(report, params, ds);

            response.setContentType("application/pdf");
            response.setHeader(
                "Content-Disposition",
                "inline; filename=HistorialGastosUsuario.pdf"
            );

            JasperExportManager.exportReportToPdfStream(
                print,
                response.getOutputStream()
            );

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
