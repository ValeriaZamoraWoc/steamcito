/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Reseñas;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.ComentariosYCalificaciones.DesabilitarComentarios;

/**
 *
 * @author cacerola
 */
@WebServlet("/desabilitarComentario")
public class srvltDeshabilitarComentario extends HttpServlet{
    
    private DesabilitarComentarios servicio = new DesabilitarComentarios();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idComentarioStr = request.getParameter("idComentario");
        String mail = request.getParameter("mail");

        Map<String, Object> resultado = new HashMap<>();
        response.setContentType("application/json");

        //datos incompletos
        if (idComentarioStr == null || idComentarioStr.isBlank() ||
            mail == null || mail.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan datos obligatorios");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        int idComentario;
        try {
            //string a int
            idComentario = Integer.parseInt(idComentarioStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "ID de comentario inválido");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        //flujo normal
        String mensaje = servicio.desabilitarComentario(idComentario, mail);
        resultado.put("exito", mensaje.equals("Comentario oculto"));
        resultado.put("mensaje", mensaje);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
    }
}
