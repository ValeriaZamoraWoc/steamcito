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

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String idComentarioStr = request.getParameter("idComentario");
        String mail = request.getParameter("mail");

        // datos incompletos
        if (idComentarioStr == null || idComentarioStr.isBlank() ||
            mail == null || mail.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan datos obligatorios");
            return;
        }

        int idComentario;
        try {
            idComentario = Integer.parseInt(idComentarioStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("ID de comentario inválido");
            return;
        }

        try {
            String mensaje = servicio.desabilitarComentario(idComentario, mail);

            if ("Comentario oculto".equals(mensaje)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

            response.getWriter().write(mensaje);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al deshabilitar comentario");
            e.printStackTrace();
        }
    }

}
