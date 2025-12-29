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

        String idJuegoSt = request.getParameter("idJuego");
        String nombreJuego = request.getParameter("nombreJuego");
        String descripcion = request.getParameter("descripcion");
        String especificaciones = request.getParameter("especificaciones");
        String clasificacionST = request.getParameter("clasificacion");
        String categoriaST = request.getParameter("categoria");
        String precioStr = request.getParameter("precio");
        String fechaStr = request.getParameter("fechaLanzamiento");

        if (idJuegoSt == null || nombreJuego.isBlank() ||
            nombreJuego == null || nombreJuego.isBlank() ||
            descripcion == null || descripcion.isBlank() ||
            clasificacionST == null || clasificacionST.isBlank() ||
            categoriaST == null || categoriaST.isBlank() ||
            precioStr == null || precioStr.isBlank() ||
            fechaStr == null || fechaStr.isBlank()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan datos obligatorios");
            return;
        }

        int precio;
        int idJuego;
        int categoria;
        int clasificacion;
        try {
            idJuego = Integer.parseInt(idJuegoSt);
            precio = Integer.parseInt(precioStr);
            categoria = Integer.parseInt(categoriaST);
            clasificacion = Integer.parseInt(clasificacionST);
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
            servicio.actualizarJuego(idJuego,nombreJuego, descripcion, especificaciones, 
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

