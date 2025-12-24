/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.MovimientoWallet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import services.MovimientoWallet.ObtenerHistorialDeGastos;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerHistorialGastos")
public class srvltObtenerHistorialDeGastos extends HttpServlet {
    
    private ObtenerHistorialDeGastos servicio = new ObtenerHistorialDeGastos();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        // Validar datos de entrada
        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            List<String> historial = servicio.obtenerHistorialGastos(mail);

            if (historial == null) {
                historial = new ArrayList<>();
            }

            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(historial));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el historial de gastos\"}");
            e.printStackTrace();
        }
    }
}