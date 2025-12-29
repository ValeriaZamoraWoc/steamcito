/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Eliminaciones;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import services.UsoJuegos.CambioClasificacion;

/**
 *
 * @author cacerola
 */
@WebServlet("/eliminarClasificacionAdmin")
public class srvltEliminarClasificacionPorAdmin extends HttpServlet{
    
    private CambioClasificacion servicio = new CambioClasificacion();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idCategoriaStr = request.getParameter("idClasificacion");

        
        Map<String, Object> resultado = new HashMap<>();
        
        response.setContentType("application/json");

        //por si galtan datos
        if (idCategoriaStr == null || idCategoriaStr.isBlank() ) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Faltan datos obligatorios");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        int idCategoria;
        try {
            
            idCategoria = Integer.parseInt(idCategoriaStr);
            
        } catch (NumberFormatException e) {
            //excepcion
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "ID de categoría inválido");
            response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
            return;
        }

        //éxito, se llama al servicio
        String mensaje = servicio.eliminarClasificacionPorAdmin(idCategoria);

        resultado.put("exito", mensaje.equals("clasificacion eliminada correctamente"));
        resultado.put("mensaje", mensaje);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(new com.google.gson.Gson().toJson(resultado));
    }
}
