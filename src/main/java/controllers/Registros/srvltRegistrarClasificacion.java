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
import services.Registros.RegistrarClasificacion;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroClasificacion")
public class srvltRegistrarClasificacion extends HttpServlet{
    private RegistrarClasificacion servicio = new RegistrarClasificacion();
    
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            System.out.println("ENTRÉ AL SERVLET REGISTRAR COMISION");
            
            String nombre = request.getParameter("nombre");
            
            if(nombre==null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre nulo");
            }
            servicio.registrarClasificacion(nombre);
        }
}
