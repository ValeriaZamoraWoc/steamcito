/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Juegos.dtoJuego;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import services.General.LocalDateAdapter;
import services.OpcionesUsuarioComun.ObtenerBibliotecaUsuario;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerBibliotecaUsuario")
public class srvltObtenerBibliotecaUsuario extends HttpServlet{
    
    private ObtenerBibliotecaUsuario servicio = new ObtenerBibliotecaUsuario();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        //datos inválidos
        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            List<dtoJuego> biblioteca = servicio.obtenerBibliotecaUsuario(mail);
            boolean visibilidad = servicio.obtenerVisibilidadBiblioteca(mail);

            if (biblioteca == null) {
                biblioteca = new ArrayList<>();
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            String bibliotecaJson = gson.toJson(biblioteca);
            String jsonResponse = String.format(
                "{\"visibilidad\":%s,\"biblioteca\":%s}",
                visibilidad,
                bibliotecaJson
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener la biblioteca\"}");
            e.printStackTrace();
        }
    }
}