/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Empresas.dtoEmpresa;
import dtos.Juegos.dtoJuego;
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
import services.OpcionesDev.ObtenerCatalogoEmpresa;
import services.OpcionesDev.ObtenerPerfilEmpresa;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerPerfilEmpresa")
public class srvltObtenerPerfilEmpresa extends HttpServlet {
    
    private ObtenerPerfilEmpresa servicioPerfil = new ObtenerPerfilEmpresa();
    private ObtenerCatalogoEmpresa servicioCatalogo = new ObtenerCatalogoEmpresa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String idEmpresaStr = request.getParameter("idEmpresa");
        // Validar datos de entrada
        if (idEmpresaStr == null || idEmpresaStr.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa requerido\"}");
            return;
        }

        try {
            Integer idEmpresa = Integer.parseInt(idEmpresaStr);
            
            //ingo básica
            dtoEmpresa empresa = servicioPerfil.obtenerPerfilEmpresa(idEmpresa);

            if (empresa == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"Empresa no encontrada\"}");
                return;
            }

            // Ver si la empresa contiene un catálogo
            //boolean incluirCatalogo = "true".equalsIgnoreCase(incluirCatalogoStr);
            List<dtoJuego> catalogo = servicioCatalogo.obtenerCatalogoEmpresa(idEmpresa);
            
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            
            response.setStatus(HttpServletResponse.SC_OK);
            
            if (catalogo == null || catalogo.isEmpty()) {
                response.getWriter().write(gson.toJson(empresa));
                
            } else {
                String respuestaJson = String.format(
                    "{\"empresa\": %s, \"catalogo\": %s, \"totalJuegos\": %d}",
                    gson.toJson(empresa),
                    gson.toJson(catalogo),
                    catalogo.size()
                );
                
                response.getWriter().write(respuestaJson);
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"ID de empresa debe ser un número\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el perfil de la empresa\"}");
            e.printStackTrace();
        }
    }
}