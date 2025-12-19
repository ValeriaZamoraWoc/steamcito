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
import services.Registros.RegistrarEmpresa;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroEmpresa")
public class srvltRegistroEmpresa extends HttpServlet{
    private RegistrarEmpresa servicio = new RegistrarEmpresa();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String nombre= request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        servicio.registrarEmpresa(nombre, descripcion);
        
    
    }
}
