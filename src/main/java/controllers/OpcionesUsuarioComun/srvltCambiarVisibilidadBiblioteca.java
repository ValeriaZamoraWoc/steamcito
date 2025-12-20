/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.OpcionesUsuarioComun;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.OpcionesUsuarioComun.CambiarVisibilidadBiblioteca;

/**
 *
 * @author cacerola
 */
@WebServlet("/cambiarVisibilidadBiblioteca")
public class srvltCambiarVisibilidadBiblioteca extends HttpServlet {
    
    private CambiarVisibilidadBiblioteca servicio = new CambiarVisibilidadBiblioteca();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> resultado = new HashMap<>();

        String mail = request.getParameter("mail");

        // Validación básica
        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Mail requerido");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {
            servicio.cambiarVisibilidadBiblioteca(mail);
            resultado.put("exito", true);
            resultado.put("mensaje", "Visibilidad de la biblioteca actualizada");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("exito", false);
            resultado.put("mensaje", "Error al cambiar la visibilidad de la biblioteca");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}
