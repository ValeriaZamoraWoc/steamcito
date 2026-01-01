/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.ReportesJasperReports.Admin;
import dtos.ReportesJasperReports.Admin.ReporteJuegosMasVendidosAdminDTO;
import dtos.ReportesJasperReports.Admin.ReporteJugadoresConMasJuegosAdminDTO;
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
@WebServlet("/admin/reporte/jugadores-con-mas-juegos")
public class srvltJugadoresConMasJuegosAdmin extends HttpServlet {

    private ReportesAdminJasperService service =
            new ReportesAdminJasperService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<ReporteJugadoresConMasJuegosAdminDTO> lista =
                    service.jugadoresConMasJuegos();

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(lista);

            InputStream jasperStream =
                    getServletContext().getResourceAsStream(
                            "/reportes/admin/JugadoresConMasJuegosAdmin.jrxml"
                    );

            JasperReport report =
                    JasperCompileManager.compileReport(jasperStream);

            JasperPrint print =
                    JasperFillManager.fillReport(report, null, dataSource);

            response.setContentType("application/pdf");
            response.setHeader(
                    "Content-Disposition",
                    "inline; filename=JugadoresConMasJuegos.pdf"
            );

            JasperExportManager.exportReportToPdfStream(
                    print,
                    response.getOutputStream()
            );

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
