/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import dtos.Usuarios.dtoGrupoFamiliar;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import services.OpcionesUsuarioComun.ObtenerGruposFamiliaresUsuario;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerGruposPorUsuario")
public class srvltObtenerGruposPorUsuario extends HttpServlet {
    
    private ObtenerGruposFamiliaresUsuario servicio = new ObtenerGruposFamiliaresUsuario();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            List<dtoGrupoFamiliar> grupos = servicio.obtenerGruposPorUsuario(mail);

            if (grupos == null || grupos.isEmpty()) {
                response.getWriter().write("lista vacía");
                return;
            }

            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(grupos));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener los grupos familiares\"}");
            e.printStackTrace();
        }
    }
}
