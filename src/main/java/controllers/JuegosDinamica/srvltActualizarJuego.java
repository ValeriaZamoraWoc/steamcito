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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.UsoJuegos.ActualizarJuego;

/**
 *
 * @author cacerola
 */
@WebServlet("/actualizarJuego")
public class srvltActualizarJuego extends HttpServlet{
    private ActualizarJuego servicio = new ActualizarJuego();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("ENTRÉ AL SERVLET ACTUALIZAR JUEGO");

        String nombreJuego = request.getParameter("nombreJuego");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String clasificacion = request.getParameter("clasificacion");
        String categoria = request.getParameter("categoria");
        String precioStr = request.getParameter("precio");
        String fechaStr = request.getParameter("fechaLanzamiento");

        if (nombreJuego == null || nombreJuego.isBlank() ||
            descripcion == null || descripcion.isBlank() ||
            clasificacion == null || clasificacion.isBlank() ||
            categoria == null || categoria.isBlank() ||
            precioStr == null || precioStr.isBlank() ||
            fechaStr == null || fechaStr.isBlank()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos obligatorios");
            return;
        }

        int precio;
        try {
            precio = Integer.parseInt(precioStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Precio inválido");
            return;
        }

        LocalDate fechaLanzamiento;
        try {
            fechaLanzamiento = LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Fecha inválida (Formato: yyyy-MM-dd)");
            return;
        }

        try {
            servicio.actualizarJuego(nombreJuego, descripcion, especificaciones, 
                                     clasificacion, categoria, precio, fechaLanzamiento);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el juego");
            e.printStackTrace();
            return;
        }

        System.out.println("JUEGO ACTUALIZADO CORRECTAMENTE");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

