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
import services.ComentariosYCalificaciones.ComentarYCalificarJuego;

/**
 *
 * @author cacerola
 */
@WebServlet("/comentarClaificarJuego")
public class srvltComentarioCalificacionJuego extends HttpServlet{
    private ComentarYCalificarJuego servicio = new ComentarYCalificarJuego();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String descripcion = request.getParameter("descripcion");
        String nombreJuego = request.getParameter("nombreJuego");
        String mail = request.getParameter("mail");
        String calificacionStr = request.getParameter("calificacion");

        if (descripcion == null || descripcion.isBlank()
                || nombreJuego == null || nombreJuego.isBlank()
                || mail == null || mail.isBlank()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Datos incompletos");
            return;
        }

        //Comentrar y calificar
        if (calificacionStr != null && !calificacionStr.isBlank()) {
            try {
                Integer calificacion = Integer.parseInt(calificacionStr);
                servicio.comentarYCalificarJuego(descripcion, nombreJuego, mail, calificacion);
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Calificación inválida");
                return;
            }
        } 
        //Solo comentar
        else {
            servicio.comentarJuego(descripcion, nombreJuego, mail);
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
