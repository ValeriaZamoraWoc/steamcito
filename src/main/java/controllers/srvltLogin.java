/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import dtos.Usuarios.dtoUsuario;
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
            response.getWriter().write("""
                {
                  "mensaje": "Login exitoso",
                  "rol": "%s",
                  "mail": "%s"
                }
            """.formatted(
                usuario.getClass().getSimpleName(),
                usuario.getMail()
            ));

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
