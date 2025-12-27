/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.OpcionesAdmin.EditarBanner;

/**
 *
 * @author cacerola
 */
@WebServlet("/editarBanner")
public class srvltEditarBanner extends HttpServlet {
    
    private EditarBanner servicio = new EditarBanner();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idJuegoNuevoStr = request.getParameter("idJuegoNuevo");

        if (idJuegoNuevoStr == null || idJuegoNuevoStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID del juego nuevo requerido\"}");
            return;
        }

        try {
            Integer idJuegoNuevo = Integer.parseInt(idJuegoNuevoStr);
            
            servicio.editarBanner(idJuegoNuevo);
            
            String respuestaJson = String.format(
                "{\"exito\": true, \"mensaje\": \"Banner actualizado correctamente\", " +
                "\"idJuegoNuevo\": %d}",
                idJuegoNuevo
            );
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(respuestaJson);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID del juego debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al actualizar el banner\"}");
            e.printStackTrace();
        }
    }
}