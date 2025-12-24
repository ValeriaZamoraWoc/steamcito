/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import services.Registros.RegistrarUsuario;

/**
 *
 * @author cacerola
 */
@WebServlet("/registrarUsuarios")
public class srvltRegistroUsuarios extends HttpServlet{
    
    private RegistrarUsuario servicio = new RegistrarUsuario();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        try {
            String tipoStr = request.getParameter("tipoUsuario");
            String mail = request.getParameter("mail");
            String nick = request.getParameter("nickname");
            String contra = request.getParameter("contrasena");
            String fechaStr = request.getParameter("fechaNacimiento");

            // datos incompletos
            if (tipoStr == null || mail == null || nick == null || contra == null || fechaStr == null ||
                tipoStr.isBlank() || mail.isBlank() || nick.isBlank() || contra.isBlank() || fechaStr.isBlank()) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Faltan parámetros obligatorios");
                return;
            }

            TipoUsuario tipo;
            try {
                tipo = TipoUsuario.valueOf(tipoStr);
            } catch (IllegalArgumentException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Tipo de usuario inválido");
                return;
            }

            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Formato de fecha inválido (yyyy-MM-dd)");
                return;
            }

            switch (tipo) {

                case Comun: {
                    String pais = request.getParameter("pais");
                    String telefonoStr = request.getParameter("telefono");

                    if (pais == null || telefonoStr == null ||
                        pais.isBlank() || telefonoStr.isBlank()) {

                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Faltan datos para usuario común");
                        return;
                    }

                    int telefono;
                    try {
                        telefono = Integer.parseInt(telefonoStr);
                    } catch (NumberFormatException e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Teléfono inválido");
                        return;
                    }

                    servicio.registrarUsuarioComun(mail, nick, contra, fecha, pais, telefono);
                    break;
                }

                case Desarrollador: {
                    String empresa = request.getParameter("empresa");

                    if (empresa == null || empresa.isBlank()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Empresa requerida para desarrollador");
                        return;
                    }

                    servicio.registrarDesarrollador(mail, nick, contra, fecha, empresa);
                    break;
                }

                case Admin:
                    servicio.registrarAdministrador(mail, nick, contra, fecha);
                    break;
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Usuario registrado correctamente");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al registrar usuario");
            e.printStackTrace();
        }
    }

}
