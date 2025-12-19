/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import com.google.gson.Gson;
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
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> resultado = new HashMap<>();

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String especificacion = request.getParameter("especificaciones");
        String clasificacion = request.getParameter("clasificacion");
        String categoria = request.getParameter("categoria");
        String empresa = request.getParameter("empresa");
        String precioSTR = request.getParameter("precio");
        String fechaLanzamientoSTR = request.getParameter("fechaLanzamiento");

        //datos incompletos
        if (nombre == null || nombre.isBlank() ||
            descripcion == null || descripcion.isBlank() ||
            especificacion == null || especificacion.isBlank() ||
            clasificacion == null || clasificacion.isBlank() ||
            categoria == null || categoria.isBlank() ||
            empresa == null || empresa.isBlank() ||
            precioSTR == null || precioSTR.isBlank() ||
            fechaLanzamientoSTR == null || fechaLanzamientoSTR.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan parámetros obligatorios");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        //validar que todo esté bien escrito
        int precio;
        try {
            precio = Integer.parseInt(precioSTR);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Precio inválido");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaLanzamientoSTR);
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Fecha inválida (Formato: yyyy-MM-dd)");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {
            //correcto
            servicio.registrarJuego(nombre, descripcion, especificacion, clasificacion, 
                                    categoria, empresa, precio, fecha);
            resultado.put("exito", true);
            resultado.put("mensaje", "Juego registrado correctamente");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("exito", false);
            resultado.put("mensaje", "Error al registrar el juego");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}
