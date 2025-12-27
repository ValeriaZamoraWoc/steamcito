/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.PeticionesComisiones;

import com.google.gson.Gson;
import dtos.Empresas.dtoPeticionComision;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.OpcionesAdmin.PeticionesComisiones;
/**
 *
 * @author cacerola
 */
@WebServlet("/rechazarPeticionComision")
public class srvltRechazarPeticion extends HttpServlet {
    
    private PeticionesComisiones servicio = new PeticionesComisiones();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idEmpresaStr = request.getParameter("idEmpresa");

        if (idEmpresaStr == null || idEmpresaStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa requerido\"}");
            return;
        }

        try {
            Integer idEmpresa = Integer.parseInt(idEmpresaStr);
            
            servicio.rechazarPeticion(idEmpresa);
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"exito\": true, \"mensaje\": \"Petición rechazada correctamente\"}");

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al rechazar la petición\"}");
            e.printStackTrace();
        }
    }
}