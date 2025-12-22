/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.JuegosDinamica;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.UsoJuegos.ActualizarJuego;

/**
 *
 * @author cacerola
 */
@WebServlet("/suspenderVentaJuego")
public class srvltSuspenderVentaJuego extends HttpServlet{
    
    private ActualizarJuego servicio = new ActualizarJuego();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String nombreJuego = request.getParameter("nombreJuego");
        String nombreEmpresa = request.getParameter("empresa");

        // Validaciones básicas
        if (nombreJuego == null || nombreJuego.isBlank() ||
            nombreEmpresa == null || nombreEmpresa.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan datos obligatorios");
            return;
        }

        try {
            servicio.suspenderVentaJuego(nombreJuego, nombreEmpresa);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Venta del juego suspendida correctamente");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al suspender la venta del juego");
            e.printStackTrace();
        }
    }

}
