/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Prestamos;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import services.Prestamos.Prestamos;

/**
 *
 * @author cacerola
 */
@WebServlet("/registrarPrestamo")
public class srvltRegistrarPrestamo extends HttpServlet {
    
    private Prestamos servicio = new Prestamos();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mailDueno = request.getParameter("mailDueno");
        String mailPrestamista = request.getParameter("mailPrestamista");
        String idJuegoStr = request.getParameter("idJuego");
        String fechaStr = request.getParameter("fecha");

        if (mailDueno == null || mailDueno.isBlank() ||
            mailPrestamista == null || mailPrestamista.isBlank() ||
            idJuegoStr == null || idJuegoStr.isBlank() ||
            fechaStr == null || fechaStr.isBlank()) {
            
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Todos los campos son requeridos\"}");
            return;
        }

        try {
            Integer idJuego = Integer.parseInt(idJuegoStr);
            LocalDate fecha = LocalDate.parse(fechaStr);
            
            servicio.registrarPrestamo(mailDueno, mailPrestamista, idJuego, fecha);
            
            // Crear respuesta JSON manualmente
            String respuestaJson = String.format(
                "{\"exito\": true, \"mensaje\": \"Préstamo registrado correctamente\", " +
                "\"mailDueno\": \"%s\", \"mailPrestamista\": \"%s\", " +
                "\"idJuego\": %d, \"fecha\": \"%s\"}",
                mailDueno, mailPrestamista, idJuego, fecha.toString()
            );
            
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(respuestaJson);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de juego debe ser un número\"}");
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Formato de fecha inválido. Use YYYY-MM-DD\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al registrar el préstamo\"}");
            e.printStackTrace();
        }
    }
}