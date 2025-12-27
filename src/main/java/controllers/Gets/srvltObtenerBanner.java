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
import java.io.IOException;
import java.time.LocalDate;
import services.General.LocalDateAdapter;
import services.OpcionesAdmin.EditarBanner;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerBanner")
public class srvltObtenerBanner extends HttpServlet {
    
    private EditarBanner servicio = new EditarBanner();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            dtoJuego banner = servicio.obtenerBanner();

            if (banner == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"No hay banner configurado\"}");
                return;
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(banner));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el banner\"}");
            e.printStackTrace();
        }
    }
}
