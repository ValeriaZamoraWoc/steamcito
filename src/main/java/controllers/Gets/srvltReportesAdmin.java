/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import services.General.LocalDateAdapter;
import services.OpcionesAdmin.ReportesAdmin;
/**
 *
 * @author cacerola
 */
@WebServlet("/reportesAdmin")
public class srvltReportesAdmin extends HttpServlet {
    
    private ReportesAdmin servicio = new ReportesAdmin();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String tipoReporte = request.getParameter("tipo");
        String clasificacion = request.getParameter("clasificacion");
        String categoria = request.getParameter("categoria");

        if (tipoReporte == null || tipoReporte.isBlank()) {
            mostrarReportesDisponibles(response);
            return;
        }

        try {
            List<String[]> datos = null;
            String tipo = "";
            String parametroExtra = "";
            
            // Procesar según el tipo de reporte
            if (tipoReporte.equalsIgnoreCase("gananciasGlobales")) {
                datos = servicio.gananciasGlobales();
                tipo = "gananciasGlobales";
            } 
            else if (tipoReporte.equalsIgnoreCase("gananciasPorEmpresa")) {
                datos = servicio.gananciasPorEmpresa();
                tipo = "gananciasPorEmpresa";
            } 
            else if (tipoReporte.equalsIgnoreCase("jugadoresConMasJuegos")) {
                datos = servicio.jugadoresConMasJuegos();
                tipo = "jugadoresConMasJuegos";
            } 
            else if (tipoReporte.equalsIgnoreCase("juegosMasVendidosClasificacion")) {
                if (clasificacion == null || clasificacion.isBlank()) {
                    enviarError(response, "Parámetro 'clasificacion' requerido");
                    return;
                }
                datos = servicio.juegosMasVendidosClasificacion(clasificacion);
                tipo = "juegosMasVendidosClasificacion";
                parametroExtra = clasificacion;
            } 
            else if (tipoReporte.equalsIgnoreCase("juegosMasVendidosCategoria")) {
                if (categoria == null || categoria.isBlank()) {
                    enviarError(response, "Parámetro 'categoria' requerido");
                    return;
                }
                datos = servicio.juegosMasVendidosCategoria(categoria);
                tipo = "juegosMasVendidosCategoria";
                parametroExtra = categoria;
            } 
            else if (tipoReporte.equalsIgnoreCase("mejoresCalificadosClasificacion")) {
                if (clasificacion == null || clasificacion.isBlank()) {
                    enviarError(response, "Parámetro 'clasificacion' requerido");
                    return;
                }
                datos = servicio.mejoresCalificadosClasificacion(clasificacion);
                tipo = "mejoresCalificadosClasificacion";
                parametroExtra = clasificacion;
            } 
            else if (tipoReporte.equalsIgnoreCase("mejoresCalificadosCategoria")) {
                if (categoria == null || categoria.isBlank()) {
                    enviarError(response, "Parámetro 'categoria' requerido");
                    return;
                }
                datos = servicio.mejoresCalificadosCategoria(categoria);
                tipo = "mejoresCalificadosCategoria";
                parametroExtra = categoria;
            } 
            else {
                enviarError(response, "Tipo de reporte no válido");
                return;
            }
            
            if (datos == null) {
                datos = new ArrayList<>();
            }

            enviarExito(response, tipo, datos, parametroExtra);

        } catch (Exception e) {
            e.printStackTrace();
            enviarError(response, "Error al obtener el reporte");
        }
    }

    private void mostrarReportesDisponibles(HttpServletResponse response) throws IOException {
        String[] reportesDisponibles = {
            "gananciasGlobales",
            "gananciasPorEmpresa", 
            "jugadoresConMasJuegos",
            "juegosMasVendidosClasificacion (requiere: clasificacion)",
            "juegosMasVendidosCategoria (requiere: categoria)",
            "mejoresCalificadosClasificacion (requiere: clasificacion)",
            "mejoresCalificadosCategoria (requiere: categoria)"
        };
        
        Gson gson = new Gson();
        String respuesta = String.format(
            "{\"exito\": true, \"mensaje\": \"Especifique un tipo de reporte\", " +
            "\"reportesDisponibles\": %s}",
            gson.toJson(reportesDisponibles)
        );
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(respuesta);
    }

    private void enviarExito(HttpServletResponse response, String tipo, 
                            List<String[]> datos, String parametroExtra) throws IOException {
        
        Gson gson = new Gson();
        String jsonDatos = gson.toJson(datos);
        
        String respuesta;
        if (!parametroExtra.isEmpty()) {
            // o clasificacion o categoria
            String nombreParametro = tipo.contains("Clasificacion") ? "clasificacion" : "categoria";
            
            respuesta = String.format(
                "{\"exito\": true, \"tipo\": \"%s\", \"%s\": \"%s\", \"total\": %d, \"datos\": %s}",
                tipo, nombreParametro, parametroExtra, datos.size(), jsonDatos
            );
        } else {
            respuesta = String.format(
                "{\"exito\": true, \"tipo\": \"%s\", \"total\": %d, \"datos\": %s}",
                tipo, datos.size(), jsonDatos
            );
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(respuesta);
    }

    private void enviarError(HttpServletResponse response, String mensaje) throws IOException {
        String respuesta = String.format("{\"mensaje\":\"%s\"}", mensaje);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(respuesta);
    }
}