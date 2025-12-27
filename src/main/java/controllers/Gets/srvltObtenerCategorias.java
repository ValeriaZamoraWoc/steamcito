/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import dtos.Juegos.dtoCategoria;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.OpcionesDev.ObtenerCategoriasClasificaciones;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerCategorias")
public class srvltObtenerCategorias extends HttpServlet {
    
    private ObtenerCategoriasClasificaciones servicio = new ObtenerCategoriasClasificaciones();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            List<dtoCategoria> categorias = servicio.obtenerCategorias();

            if (categorias == null) {
                categorias = new ArrayList<>();
            }

            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(categorias));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener las categorías\"}");
            e.printStackTrace();
        }
    }
}