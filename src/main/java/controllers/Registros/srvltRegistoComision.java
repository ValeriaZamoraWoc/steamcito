/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.Registros.RegistrarComision;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroComision")
public class srvltRegistoComision extends HttpServlet{
    
    private RegistrarComision servicio = new RegistrarComision();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        System.out.println("ENTRÉ AL SERVLET REGISTRAR COMISION");
        
        String porcentajeStr = request.getParameter("porcentaje");

        if (porcentajeStr == null || porcentajeStr.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Porcentaje requerido");
            return;
        }

        int porcentaje;
        try {
            porcentaje = Integer.parseInt(porcentajeStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Porcentaje inválido");
            return;
        }

        servicio.registrarComision(porcentaje);
        System.out.println("DESPUÉS DEL SERVICE");
    }
}
