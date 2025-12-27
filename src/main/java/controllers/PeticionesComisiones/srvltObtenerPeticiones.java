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
@WebServlet("/obtenerPeticionesComisiones")
public class srvltObtenerPeticiones extends HttpServlet {
    
    private PeticionesComisiones servicio = new PeticionesComisiones();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            List<dtoPeticionComision> peticiones = servicio.obtenerPeticiones();

            if (peticiones == null) {
                peticiones = new ArrayList<>();
            }

            Gson gson = new Gson();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(peticiones));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener las peticiones\"}");
            e.printStackTrace();
        }
    }
}