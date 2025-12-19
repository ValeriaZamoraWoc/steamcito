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
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> resultado = new HashMap<>();

        String empresa = request.getParameter("empresa");
        String porcentajeStr = request.getParameter("porcentaje");

        //datos incompletos
        if (empresa == null || empresa.isBlank() || porcentajeStr == null || porcentajeStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan parámetros obligatorios");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        int porcentaje;
        try {
            porcentaje = Integer.parseInt(porcentajeStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Porcentaje inválido");
            response.getWriter().write(new Gson().toJson(resultado));
            return;
        }

        try {
            servicio.registrarEmpresa(empresa, porcentaje);
            resultado.put("exito", true);
            resultado.put("mensaje", "Petición registrada correctamente");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("exito", false);
            resultado.put("mensaje", "Error al registrar petición");
            e.printStackTrace();
        }

        response.getWriter().write(new Gson().toJson(resultado));
    }
}
