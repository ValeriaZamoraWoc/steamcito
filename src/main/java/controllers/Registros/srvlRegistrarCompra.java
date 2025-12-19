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
import services.Registros.RegistrarCompra;

/**
 *
 * @author cacerola
 */
@WebServlet("/registrarCompra")
public class srvlRegistrarCompra extends HttpServlet {
    private RegistrarCompra servicio= new RegistrarCompra();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreJuego = request.getParameter("juego");
        String mail = request.getParameter("mail");
        
        servicio.registrarCompra(nombreJuego, mail);
    }
}
