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
        response.setContentType("text/plain;charset=UTF-8");

        String mail = request.getParameter("mail");

        //datos incompletos
        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Mail requerido");
            return;
        }

        try {
            servicio.cambiarVisibilidadBiblioteca(mail);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Visibilidad de la biblioteca actualizada");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al cambiar la visibilidad de la biblioteca");
            e.printStackTrace();
        }
    }

}
