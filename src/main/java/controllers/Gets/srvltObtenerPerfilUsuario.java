/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import services.General.LocalDateAdapter;
import services.OpcionesUsuarioComun.ObtenerPerfilUsuarioComun;

/**
 *
 * @author cacerola
 */
@WebServlet("/obtenerPerfilUsuarioComun")
public class srvltObtenerPerfilUsuario extends HttpServlet {
    
    private ObtenerPerfilUsuarioComun servicio = new ObtenerPerfilUsuarioComun();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String mail = request.getParameter("mail");

        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            dtoUsuario usuario = servicio.obtenerUsuarioComun(mail);

            if (usuario == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"Usuario no encontrado\"}");
                return;
            }

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            List<dtoJuego> biblioteca = servicio.obtenerBibliotecaUsuario(mail);
            boolean visibilidad = servicio.obtenerVisibilidadBiblioteca(mail);
            
            String usuarioJson = gson.toJson(usuario);
            
            if (biblioteca != null && !biblioteca.isEmpty()) {
                String bibliotecaJson = gson.toJson(biblioteca);
                String respuestaJson = String.format(
                    "{\"visibilidad\":%s,\"usuario\":%s,\"biblioteca\":%s}",
                    visibilidad,
                    usuarioJson,
                    bibliotecaJson
                );
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(respuestaJson);
            } else {
                String respuestaJson = String.format(
                    "{\"visibilidad\":%s,\"usuario\":%s}",
                    visibilidad,
                    usuarioJson
                );
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(respuestaJson);
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el perfil del usuario\"}");
            e.printStackTrace();
        }
    }
}
