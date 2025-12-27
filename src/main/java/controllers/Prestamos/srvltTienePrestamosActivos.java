/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Prestamos;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.Prestamos.Prestamos;
/**
 *
 * @author cacerola
 */
@WebServlet("/tienePrestamosActivos")
public class srvltTienePrestamosActivos extends HttpServlet {
    
    private Prestamos servicio = new Prestamos();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            boolean tienePrestamosActivos = servicio.tienePrestamosActivos(mail);
            
            String respuestaJson = String.format(
                "{\"exito\": true, \"mail\": \"%s\", \"tienePrestamosActivos\": %b}",
                mail, tienePrestamosActivos
            );
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(respuestaJson);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al verificar préstamos activos\"}");
            e.printStackTrace();
        }
    }
}
