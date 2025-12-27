/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioDesarrollador;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.Login.Login;

/**
 *
 * @author cacerola
 */
@WebServlet("/login")
public class srvltLogin extends HttpServlet {
    private Login servicio = new Login();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String mail = request.getParameter("mail");
            String password = request.getParameter("password");

            dtoUsuario usuario = servicio.login(mail, password);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");

            String rol = usuario.getClass().getSimpleName();
            String correo = usuario.getMail();
            String jsonFinal;

            if (usuario instanceof dtoUsuarioDesarrollador dev) {
                jsonFinal = """
                    {
                      "rol": "%s",
                      "mail": "%s",
                      "idEmpresa": %d
                    }
                """.formatted(rol, correo, dev.getIdEmpresa());
            } else {
                jsonFinal = """
                    {
                      "rol": "%s",
                      "mail": "%s"
                    }
                """.formatted(rol, correo);
            }

            response.getWriter().write(jsonFinal);

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("""
                {
                  "error": "%s"
                }
            """.formatted(e.getMessage()));
        }
    }
}
