/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import com.google.gson.Gson;
import dtos.Empresas.dtoEmpresa;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.Registros.RegistrarEmpresa;

/**
 *
 * @author cacerola
 */
@WebServlet("/registroEmpresa")
public class srvltRegistroEmpresa extends HttpServlet{
    private RegistrarEmpresa servicio = new RegistrarEmpresa();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");

        if (nombre == null || nombre.isBlank() ||
            descripcion == null || descripcion.isBlank()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Faltan parámetros obligatorios");
            return;
        }

        try {
            servicio.registrarEmpresa(nombre, descripcion);
            dtoEmpresa empresa = servicio.buscarEmpresaPorNombre(nombre);
            String id = String.valueOf(empresa.getIdEmpresa());
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(id);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar la empresa");
            e.printStackTrace();
        }
    }

}
