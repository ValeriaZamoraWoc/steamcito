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
import models.Usuarios.TipoUsuario;
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
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> resultado = new HashMap<>();

        try {
            String tipoStr = request.getParameter("tipoUsuario");
            String mail = request.getParameter("mail");
            String nick = request.getParameter("nickname");
            String contra = request.getParameter("contrasena");
            String fechaStr = request.getParameter("fechaNacimiento");

            //datos incompletos
            if (tipoStr == null || mail == null || nick == null || contra == null || fechaStr == null ||
                tipoStr.isBlank() || mail.isBlank() || nick.isBlank() || contra.isBlank() || fechaStr.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resultado.put("exito", false);
                resultado.put("mensaje", "Faltan parámetros obligatorios");
                response.getWriter().write(new Gson().toJson(resultado));
                return;
            }

            TipoUsuario tipo = TipoUsuario.valueOf(tipoStr);
            LocalDate fecha;
            try {
                fecha = LocalDate.parse(fechaStr);
            } catch (DateTimeParseException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resultado.put("exito", false);
                resultado.put("mensaje", "Formato de fecha inválido (yyyy-MM-dd)");
                response.getWriter().write(new Gson().toJson(resultado));
                return;
            }

            switch (tipo) {
                case Comun:
                {
                    String pais = request.getParameter("pais");
                    String telefonoStr = request.getParameter("telefono");
                    if (pais == null || telefonoStr == null || pais.isBlank() || telefonoStr.isBlank()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resultado.put("exito", false);
                        resultado.put("mensaje", "Faltan datos para usuario común");
                        response.getWriter().write(new Gson().toJson(resultado));
                        return;
                    }
                    int telefono;
                    try {
                        telefono = Integer.parseInt(telefonoStr);
                    } catch (NumberFormatException e) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resultado.put("exito", false);
                        resultado.put("mensaje", "Teléfono inválido");
                        response.getWriter().write(new Gson().toJson(resultado));
                        return;
                    }
                    servicio.registrarUsuarioComun(mail, nick, contra, fecha, pais, telefono);
                }
                case Desarrollador:
                {
                    String empresa = request.getParameter("empresa");
                    if (empresa == null || empresa.isBlank()) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resultado.put("exito", false);
                        resultado.put("mensaje", "Empresa no encontrada para desarrollador");
                        response.getWriter().write(new Gson().toJson(resultado));
                        return;
                    }
                    servicio.registrarDesarrollador(mail, nick, contra, fecha, empresa);
                }
                case Admin :
                    servicio.registrarAdministrador(mail, nick, contra, fecha);
            }

            resultado.put("exito", true);
            resultado.put("mensaje", "Usuario registrado correctamente");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(new Gson().toJson(resultado));

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultado.put("exito", false);
            resultado.put("mensaje", "Tipo de usuario inválido");
            response.getWriter().write(new Gson().toJson(resultado));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resultado.put("exito", false);
            resultado.put("mensaje", "Error al registrar usuario");
            e.printStackTrace();
            response.getWriter().write(new Gson().toJson(resultado));
        }
    }
}
