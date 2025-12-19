/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.UsoJuegos;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.UsoJuegos.EliminarCategoria;

/**
 *
 * @author cacerola
 */
@WebServlet("/cambiarCategoriaDesarrollador")
public class srvltCambiarCategoriaJuegoDesarrollador extends HttpServlet{
    private EliminarCategoria servicio = new EliminarCategoria();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idCategoriaStr = request.getParameter("idCategoria");
        String idJuegoStr = request.getParameter("idJuego");
        String mail = request.getParameter("mail");

        Map<String, Object> resultado = new HashMap<>();
        response.setContentType("application/json");

        //datos incompletos
        if (idCategoriaStr == null || idCategoriaStr.isBlank() ||
            idJuegoStr == null || idJuegoStr.isBlank() ||
            mail == null || mail.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan datos obligatorios");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        int idCategoria;
        int idJuego;
        try {
            //de string a int
            idCategoria = Integer.parseInt(idCategoriaStr);
            idJuego = Integer.parseInt(idJuegoStr);
        } catch (NumberFormatException e) {
            //excepcion
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "ID inválido");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        //cambio de categoria correcto
        String mensaje = servicio.cambiarCategoriaDeJuegoPorDesarrollador(idCategoria, idJuego, mail);

        resultado.put("exito", mensaje.equals("Categoría cambiada correctamente"));
        resultado.put("mensaje", mensaje);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
    }
}
