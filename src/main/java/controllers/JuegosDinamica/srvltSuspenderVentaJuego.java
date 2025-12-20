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
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> resultado = new HashMap<>();

        String nombreJuego = request.getParameter("nombreJuego");
        String nombreEmpresa = request.getParameter("empresa");

        // Validaciones básicas
        if (nombreJuego == null || nombreJuego.isBlank() ||
            nombreEmpresa == null || nombreEmpresa.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan datos obligatorios");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {
            servicio.suspenderVentaJuego(nombreJuego, nombreEmpresa);
            resultado.put("exito", true);
            resultado.put("mensaje", "Venta del juego suspendida correctamente");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("exito", false);
            resultado.put("mensaje", "Error al suspender la venta del juego");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}
