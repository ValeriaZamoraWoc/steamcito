/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.GruposFamiliares;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import services.OpcionesUsuarioComun.SacarUsuarioGrupoFamiliar;

/**
 *
 * @author cacerola
 */

@WebServlet("/sacarUsuarioGrupoFamiliar")
public class srvltEliminarUsuarioGrupoFamiliar extends HttpServlet{
    
    private SacarUsuarioGrupoFamiliar servicio = new SacarUsuarioGrupoFamiliar();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");

        String idGrupoStr = request.getParameter("idGrupo");
        String mail = request.getParameter("mail");

        // datos incompletos
        if (idGrupoStr == null || idGrupoStr.isBlank() ||
            mail == null || mail.isBlank()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Faltan datos obligatorios");
            return;
        }

        int idGrupo;
        try {
            idGrupo = Integer.parseInt(idGrupoStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "ID de grupo inválido");
            return;
        }

        try {
            servicio.sacarUsuarioDeGrupo(idGrupo, mail);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Usuario eliminado del grupo familiar");

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al eliminar usuario del grupo");
            e.printStackTrace();
        }
    }
}
