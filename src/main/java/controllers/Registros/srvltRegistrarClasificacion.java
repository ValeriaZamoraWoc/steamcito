/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.Registros.RegistrarClasificacion;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroClasificacion")
public class srvltRegistrarClasificacion extends HttpServlet {

    private RegistrarClasificacion servicio = new RegistrarClasificacion();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");

        String nombre = request.getParameter("nombre");

        if (nombre == null || nombre.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("""
                { "exito": false, "mensaje": "Nombre inválido" }
            """);
            return;
        }

        servicio.registrarClasificacion(nombre);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("""
            { "exito": true, "mensaje": "Clasificación creada" }
        """);
    }
}

