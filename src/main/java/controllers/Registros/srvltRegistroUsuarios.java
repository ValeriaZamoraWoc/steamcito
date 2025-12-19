/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Registros;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
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

        System.out.println("ENTRÉ AL SERVLET REGISTRAR");

        String tipoStr = request.getParameter("tipoUsuario");
        TipoUsuario tipo = TipoUsuario.valueOf(tipoStr);
        
        String mail = request.getParameter("mail");
        String nick = request.getParameter("nickname");
        String contra = request.getParameter("contrasena");
        String fechaStr = request.getParameter("fechaNacimiento");
        LocalDate fecha = LocalDate.parse(fechaStr);
        
        System.out.println("ANTES DEL SERVICE");
        
        switch(tipo){
            case Comun:
                String pais = request.getParameter("pais");
                String telefonoStr = request.getParameter("telefono");
                
                int telefono = Integer.parseInt(telefonoStr);
                servicio.registrarUsuarioComun(mail, nick, contra, fecha, contra, pais, telefono);
                break;
            case Desarrollador:
                String empresa = request.getParameter("empresa");
                if (empresa == null) {
                    throw new IllegalStateException("Empresa no encontrada para desarrollador");
                }
                servicio.registrarDesarrollador(mail, nick, contra, fecha, empresa);
                break;
            case Admin:
                servicio.registrarAdministrador(mail, nick, contra, fecha);
        }
        
        
        System.out.println("DESPUÉS DEL SERVICE");
        
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println("<h1>Usuario registrado</h1>");
    }
}
