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
import java.util.HashMap;
import java.util.Map;
import services.General.LocalDateAdapter;
import services.OpcionesDev.ReportesEmpresa;

/**
 *
 * @author cacerola
 */
@WebServlet("/reportesEmpresa")
public class srvltReportesEmpresa extends HttpServlet {
    
    private ReportesEmpresa servicio = new ReportesEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idEmpresaStr = request.getParameter("idEmpresa");
        String tipoReporte = request.getParameter("tipo");

        // Validar datos de entrada
        if (idEmpresaStr == null || idEmpresaStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa requerido\"}");
            return;
        }

        try {
            Integer idEmpresa = Integer.parseInt(idEmpresaStr);
            
            // Crear Gson con LocalDateAdapter
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            
            Object resultado = null;
            String tipo = "";
            
            // Determinar qué reporte devolver
            if (tipoReporte == null || tipoReporte.isBlank()) {
                // Si no se especifica tipo, devolver todos
                Map<String, Object> reportesCompletos = new HashMap<>();
                reportesCompletos.put("ventas", servicio.obtenerVentasEmpresa(idEmpresa));
                reportesCompletos.put("mejorCalificados", servicio.obtenerJuegosMejorCalificadosEmpresa(idEmpresa));
                reportesCompletos.put("peorCalificados", servicio.obtenerJuegosPeorCalificadosEmpresa(idEmpresa));
                reportesCompletos.put("top5", servicio.obtenerTop5(idEmpresa));
                
                resultado = reportesCompletos;
                tipo = "completo";
            } else {
                switch (tipoReporte.toLowerCase()) {
                    case "ventas":
                        resultado = servicio.obtenerVentasEmpresa(idEmpresa);
                        tipo = "ventas";
                        break;
                    case "mejorcalificados":
                        resultado = servicio.obtenerJuegosMejorCalificadosEmpresa(idEmpresa);
                        tipo = "mejorCalificados";
                        break;
                    case "peorcalificados":
                        resultado = servicio.obtenerJuegosPeorCalificadosEmpresa(idEmpresa);
                        tipo = "peorCalificados";
                        break;
                    case "top5":
                        resultado = servicio.obtenerTop5(idEmpresa);
                        tipo = "top5";
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("{\"mensaje\":\"Tipo de reporte no válido\"}");
                        return;
                }
            }
            
            if (resultado == null) {
                if (tipoReporte != null) {
                    resultado = new ArrayList<>();
                } else {
                    resultado = new HashMap<>();
                }
            }

            String jsonResponse = gson.toJson(resultado);
            String respuestaFinal = String.format(
                "{\"exito\": true, \"idEmpresa\": %d, \"tipo\": \"%s\", \"datos\": %s}",
                idEmpresa,
                tipo,
                jsonResponse
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(respuestaFinal);

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener los reportes\"}");
            e.printStackTrace();
        }
    }
}