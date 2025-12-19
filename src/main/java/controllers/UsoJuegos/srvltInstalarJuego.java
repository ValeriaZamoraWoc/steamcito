/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.UsoJuegos;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import services.UsoJuegos.InstalarJuego;
import com.google.gson.Gson;
import java.util.HashMap;


/**
 *
 * @author cacerola
 */
@WebServlet("/instalarJuego")
public class srvltInstalarJuego extends HttpServlet {
    private InstalarJuego servicio = new InstalarJuego();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");
        String nombreJuego = request.getParameter("juego");

        Map<String, Object> resultado = new HashMap<>();

        if (mail == null || mail.isBlank() || nombreJuego == null || nombreJuego.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("instalado", false);
            resultado.put("mensaje", "Faltan parámetros obligatorios: mail y juego");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {
            resultado = servicio.intentarInstalarJuego(nombreJuego, mail);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("instalado", false);
            resultado.put("mensaje", "Error al intentar instalar el juego");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}
