/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Prestamos;

import dtos.ObjetosUsuario.dtoPrestamo;
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
@WebServlet("/devolverPrestamo")
public class srvltDevolverPrestamo extends HttpServlet {
    
    private Prestamos servicio = new Prestamos();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");


        String mailPrestamista = request.getParameter("mailPrestamista");

        if (mailPrestamista == null || mailPrestamista.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"mailPrestamista es requerido\"}");
            return;
        }

        try {
            servicio.devolverUnPrestamo(mailPrestamista);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("""
                {
                  "exito": true,
                  "mensaje": "Préstamo devuelto correctamente",
                  "mailPrestamista": "%s"
                }
            """.formatted(mailPrestamista));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de juego debe ser un número\"}");
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Formato de fecha inválido. Use YYYY-MM-DD\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al devolver el préstamo\"}");
            e.printStackTrace();
        }
    }
}