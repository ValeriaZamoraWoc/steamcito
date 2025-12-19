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
import services.Registros.RegistrarPeticionComision;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroPeticionComision")
public class srvltRegistroPeticionComision extends HttpServlet{
    private RegistrarPeticionComision servicio = new RegistrarPeticionComision();
    
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String empresa = request.getParameter("empresa");
        String porcentajeSTR = request.getParameter("porcentaje");
        Integer porcentaje = Integer.parseInt(porcentajeSTR);
        
        servicio.registrarEmpresa(empresa, porcentaje);
    }

}
