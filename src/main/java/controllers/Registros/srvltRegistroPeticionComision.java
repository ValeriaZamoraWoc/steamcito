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
import java.util.HashMap;
import java.util.Map;
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

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String empresa = request.getParameter("empresa");
        String porcentajeStr = request.getParameter("porcentaje");

        // datos incompletos
        if (empresa == null || empresa.isBlank() ||
            porcentajeStr == null || porcentajeStr.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan parámetros obligatorios");
            return;
        }

        int porcentaje;
        try {
            porcentaje = Integer.parseInt(porcentajeStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Porcentaje inválido");
            return;
        }

        try {
            boolean b =servicio.registrarEmpresa(empresa, porcentaje);
            
            if(b){
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Petición registrada correctamente");
            }else{
                response.getWriter().write("No se puede registrar otra petición");
            }
            

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar petición");
            e.printStackTrace();
        }
    }

}
