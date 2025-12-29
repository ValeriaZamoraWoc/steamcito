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
import services.UsoJuegos.DesinstalarJuego;

/**
 *
 * @author cacerola
 */
@WebServlet("/desinstalarJuego")
public class srvltDesinstalarJuego extends HttpServlet {
    private DesinstalarJuego servicio = new DesinstalarJuego();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");
        String idJuegoST = request.getParameter("idJuego");

        Map<String, Object> resultado = new HashMap<>();

        // Validar parámetros
        if (mail == null || mail.isBlank() || idJuegoST == null || idJuegoST.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("desinstalado", false);
            resultado.put("mensaje", "Faltan parámetros obligatorios: mail y juego");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {    
            Integer idJuego = Integer.parseInt(idJuegoST);
            if (idJuego == null || idJuego==0) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resultado.put("desinstalado", false);
                resultado.put("mensaje", "Juego no encontrado: " + idJuegoST);
                response.getWriter().write(new Gson().toJson(resultado));
                return;
            }
            // Intentar desinstalar el juego
            servicio.desinstalarJuego(mail,idJuego);
            
            resultado.put("desinstalado", true);
            resultado.put("mensaje", "Juego desinstalado correctamente");
            resultado.put("juego", idJuego);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("desinstalado", false);
            resultado.put("mensaje", "Error al intentar desinstalar el juego");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}