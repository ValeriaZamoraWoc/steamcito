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


/**
 *
 * @author cacerola
 */
@WebServlet("/instalarJuego")
public class srvltInstalarJuego extends HttpServlet {
    private InstalarJuego servicio = new InstalarJuego();
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mail = request.getParameter("mail");
        String juego = request.getParameter("juego");

        Map<String, Object> resultado =
            servicio.intentarInstalarJuego(juego, mail);

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(resultado));
    }

}
