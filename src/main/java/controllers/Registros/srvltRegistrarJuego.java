/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import com.google.gson.Gson;
import dtos.Juegos.dtoJuego;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import services.Registros.RegistrarJuego;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroJuego")
public class srvltRegistrarJuego extends HttpServlet{
    private RegistrarJuego servicio = new RegistrarJuego();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String especificacion = request.getParameter("especificaciones");
        String clasificacion = request.getParameter("clasificacion");
        String categoria = request.getParameter("categoria");
        String empresa = request.getParameter("empresa");
        String precioSTR = request.getParameter("precio");
        String fechaLanzamientoSTR = request.getParameter("fechaLanzamiento");

        // Validación de datos obligatorios
        if (nombre == null || nombre.isBlank() ||
            descripcion == null || descripcion.isBlank() ||
            especificacion == null || especificacion.isBlank() ||
            clasificacion == null || clasificacion.isBlank() ||
            categoria == null || categoria.isBlank() ||
            empresa == null || empresa.isBlank() ||
            precioSTR == null || precioSTR.isBlank() ||
            fechaLanzamientoSTR == null || fechaLanzamientoSTR.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan parámetros obligatorios");
            return;
        }

        int precio;
        try {
            precio = Integer.parseInt(precioSTR);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Precio inválido");
            return;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaLanzamientoSTR);
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Fecha inválida (Formato: yyyy-MM-dd)");
            return;
        }

        try {
            dtoJuego juego =servicio.registrarJuego(
                nombre, descripcion, especificacion,
                clasificacion, categoria, empresa,
                precio, fecha
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("""
            {
              "idJuego": %d,
              "mensaje": "Juego creado. Falta imagen."
            }
            """.formatted(juego.getId()));


        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar el juego");
            e.printStackTrace();
        }
        
    }

}
