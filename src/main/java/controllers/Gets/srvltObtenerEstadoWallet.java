/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import dtos.ObjetosUsuario.dtoWallet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.MovimientoWallet.ObtenerEstadoWallet;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerEstadoWallet")
public class srvltObtenerEstadoWallet extends HttpServlet {
    
    private ObtenerEstadoWallet servicio = new ObtenerEstadoWallet();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"exito\": false, \"mensaje\": \"Mail requerido\"}");
            return;
        }

        try {
            dtoWallet wallet = servicio.obtenerEstadoWallet(mail);

            if (wallet == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("""
                    { 
                        "exito": false, 
                        "mensaje": "No se encontró wallet para el usuario"
                    }
                """);
                return;
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(wallet);
            
            String respuestaFinal = String.format(
                "{\"exito\": true, \"wallet\": %s}", 
                jsonResponse
            );

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(respuestaFinal);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("""
                { 
                    "exito": false, 
                    "mensaje": "Error al obtener el estado de la wallet"
                }
            """);
        }
    }
}