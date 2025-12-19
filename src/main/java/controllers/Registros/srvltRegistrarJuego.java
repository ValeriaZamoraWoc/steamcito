/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
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
    
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String especificacion = request.getParameter("especificaciones");
        String clasificacion = request.getParameter("clasificacion");
        String categoria = request.getParameter("categoria");
        String empresa = request.getParameter("empresa");
        String precioSTR = request.getParameter("precio");
        String fechaLanzamientoSTR = request.getParameter("fechaLanzamiento");
        
        int precio= Integer.parseInt(precioSTR);
        if (precioSTR == null) {
            System.out.println("POST vacío, ignorado");
            return;
        }
        LocalDate fecha = LocalDate.parse(fechaLanzamientoSTR);
        
        servicio.registrarJuego(nombre, descripcion, especificacion, clasificacion, 
                categoria, empresa, precio, fecha);
    }
}
