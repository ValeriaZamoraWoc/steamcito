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
import java.util.ArrayList;
import java.util.List;
import services.General.LocalDateAdapter;
import services.OpcionesDev.ObtenerCatalogoEmpresa;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerCatalogoEmpresa")
public class srvltObtenerCatalogoEmpresa extends HttpServlet {
    
    private ObtenerCatalogoEmpresa servicio = new ObtenerCatalogoEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idEmpresaStr = request.getParameter("idEmpresa");

        if (idEmpresaStr == null || idEmpresaStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa requerido\"}");
            return;
        }

        try {
            Integer idEmpresa = Integer.parseInt(idEmpresaStr);
            List<dtoJuego> catalogo = servicio.obtenerCatalogoEmpresa(idEmpresa);

            if (catalogo == null) {
                catalogo = new ArrayList<>();
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(catalogo));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el catálogo de la empresa\"}");
            e.printStackTrace();
        }
    }
}