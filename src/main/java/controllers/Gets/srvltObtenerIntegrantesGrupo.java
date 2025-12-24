/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Usuarios.dtoUsuarioComun;
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
import services.OpcionesUsuarioComun.ObtenerIntegrantesGruposFamiliares;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerIntegrantesGrupo")
public class srvltObtenerIntegrantesGrupo extends HttpServlet {
    
    private ObtenerIntegrantesGruposFamiliares servicio = new ObtenerIntegrantesGruposFamiliares();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idGrupoStr = request.getParameter("idGrupo");

        if (idGrupoStr == null || idGrupoStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID del grupo requerido\"}");
            return;
        }

        try {
            int idGrupo = Integer.parseInt(idGrupoStr);
            List<dtoUsuarioComun> integrantes = servicio.obtenerIntegrantesGrupo(idGrupo);

            if (integrantes == null) {
                integrantes = new ArrayList<>();
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(integrantes));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID del grupo debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener los integrantes\"}");
            e.printStackTrace();
        }
    }
}