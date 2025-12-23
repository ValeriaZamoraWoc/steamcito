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
import services.ComentariosYCalificaciones.ComentarYCalificarComentario;

/**
 *
 * @author cacerola
 */
@WebServlet("/comentarComentario")
public class srvltComentarioCalificacionComentarios extends HttpServlet{
    
    private ComentarYCalificarComentario servicio = new ComentarYCalificarComentario();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String descripcion = request.getParameter("descripcion");
        String nombreJuego = request.getParameter("nombreJuego");
        String mail = request.getParameter("mail");
        String calificacionStr = request.getParameter("calificacion");
        String idComentarioPadreStr = request.getParameter("idComentarioPadre");

        if (descripcion == null || descripcion.isBlank() ||
            nombreJuego == null || nombreJuego.isBlank() ||
            mail == null || mail.isBlank()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos");
            return;
        }

        Integer calificacion = null;
        if (calificacionStr != null && !calificacionStr.isBlank()) {
            try {
                calificacion = Integer.parseInt(calificacionStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Calificación inválida");
                return;
            }
        }

        Integer idComentarioPadre = null;
        if (idComentarioPadreStr != null && !idComentarioPadreStr.isBlank()) {
            try {
                idComentarioPadre = Integer.parseInt(idComentarioPadreStr);
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de comentario padre inválido");
                return;
            }
        }
        
        if (idComentarioPadre != null) {
            servicio.comentarYCalificarComentario(descripcion, nombreJuego, mail, calificacion, idComentarioPadre);
        } else {
            servicio.comentarComentario(descripcion, nombreJuego, mail);
        }

        System.out.println("SALIENDO DEL SERVLET COMENTAR COMENTARIO");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
