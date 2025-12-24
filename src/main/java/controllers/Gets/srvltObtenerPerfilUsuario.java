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

        // Validar datos de entrada
        if (mail == null || mail.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Mail requerido\"}");
            return;
        }

        try {
            // Obtener información básica del usuario
            dtoUsuario usuario = servicio.obtenerUsuarioComun(mail);

            if (usuario == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"Usuario no encontrado\"}");
                return;
            }

            // Crear el Gson de solo el usuario sin biblioteca
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();

            // Determinar si se debe incluir la biblioteca
            boolean incluirBiblioteca = servicio.obtenerBibliotecaUsuario(mail)!= null;
            
            if (incluirBiblioteca) {
                List<dtoJuego> biblioteca = servicio.obtenerBibliotecaUsuario(mail);
                
                if (biblioteca == null || biblioteca.isEmpty()) {
                    // Si biblioteca es nula o vacía, devolver solo el usuario
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(gson.toJson(usuario));
                } else {
                    // Si hay biblioteca, agregar al json
                    String respuestaJson = String.format(
                        "{\"usuario\": %s, \"biblioteca\": %s}",
                        gson.toJson(usuario),
                        gson.toJson(biblioteca)
                    );
                    
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(respuestaJson);
                }
            } else {
                // Si no se solicita biblioteca, solo devolver el usuario
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(usuario));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error al obtener el perfil del usuario\"}");
            e.printStackTrace();
        }
    }
}
