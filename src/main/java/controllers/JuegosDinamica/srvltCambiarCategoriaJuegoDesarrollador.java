/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.JuegosDinamica;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.UsoJuegos.CambioClasificacion;

/**
 *
 * @author cacerola
 */
@WebServlet("/cambiarCategoriaDesarrollador")
public class srvltCambiarCategoriaJuegoDesarrollador extends HttpServlet{
    private CambioClasificacion servicio = new CambioClasificacion();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        String idCategoriaStr = request.getParameter("idCategoria");
        String idJuegoStr = request.getParameter("idJuego");
        String mail = request.getParameter("mail");

        // Validación de datos obligatorios
        if (idCategoriaStr == null || idCategoriaStr.isBlank() ||
            idJuegoStr == null || idJuegoStr.isBlank() ||
            mail == null || mail.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan datos obligatorios");
            return;
        }

        int idCategoria;
        int idJuego;

        try {
            idCategoria = Integer.parseInt(idCategoriaStr);
            idJuego = Integer.parseInt(idJuegoStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID inválido");
            return;
        }

//este servlet ya no funcionará

    }

}
