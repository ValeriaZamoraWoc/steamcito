/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.OpcionesUsuarioComun.ReportesUsuarioComun;

/**
 *
 * @author cacerola
 */
@WebServlet("/reportesUsuario")
public class srvltReportesUsuario extends HttpServlet {
    
    private ReportesUsuarioComun servicio = new ReportesUsuarioComun();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");
        String tipo = request.getParameter("tipo");

        Gson gson = new Gson();

        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\":false,\"mensaje\":\"Parámetro 'mail' requerido\"}");
            return;
        }

        try {
            List<String[]> datos = null;
            
            if (tipo == null || tipo.isBlank()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"mensaje\":\"Especifica tipo: mejoresCalificadosBiblioteca, calificacionesPersonales o clasificacionesFavoritas\"}");
                return;
            } 
            else if (tipo.equalsIgnoreCase("mejoresCalificadosBiblioteca")) {
                datos = servicio.obtenerMejoresCalificadosBiblioteca(mail);
            } 
            else if (tipo.equalsIgnoreCase("calificacionesPersonales")) {
                datos = servicio.obtenerCalificacionesPersonales(mail);
            } 
            else if (tipo.equalsIgnoreCase("clasificacionesFavoritas")) {
                datos = servicio.obtenerClasificacionesFavoritas(mail);
            }
            else if (tipo.equalsIgnoreCase("prestadosMasTiempo")) {
                datos = servicio.obtenerPrestadosMasJugados(mail);
            } 
            else if (tipo.equalsIgnoreCase("prestadosMejorCalificados")) {
                datos = servicio.obtenerMejoresCalificadosPrestados(mail);
            } 
            else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\":false,\"mensaje\":\"Tipo no válido\"}");
                return;
            }
            
            if (datos == null) datos = new ArrayList<>();
            
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"success\":true,\"mail\":\"" + mail + "\",\"tipo\":\"" + tipo + "\",\"data\":" + gson.toJson(datos) + ",\"cantidad\":" + datos.size() + "}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\":false,\"mensaje\":\"Error interno del servidor\"}");
            e.printStackTrace();
        }
    }
}