/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Reseñas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Juegos.dtoComentario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import services.ComentariosYCalificaciones.ObtenerComentariosPorJuego;
import services.General.LocalDateAdapter;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerComentariosPorJuego")
public class srvltObtenerComentariosPorJuego extends HttpServlet{
    
    private ObtenerComentariosPorJuego servicio = new ObtenerComentariosPorJuego();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idJuegoStr = request.getParameter("idJuego");

        // Validación básica
        if (idJuegoStr == null || idJuegoStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                "{\"mensaje\":\"idJuego requerido\"}"
            );
            return;
        }

        int idJuego;
        try {
            idJuego = Integer.parseInt(idJuegoStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(
                "{\"mensaje\":\"idJuego inválido\"}"
            );
            return;
        }

        try {
            List<dtoComentario> comentarios =
                    servicio.obtenerComentariosPorJuego(idJuego);

            if (comentarios == null) {
                comentarios = new ArrayList<>();
            }


            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(comentarios));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(
                "{\"mensaje\":\"Error al obtener comentarios\"}"
            );
        }
    }
}
