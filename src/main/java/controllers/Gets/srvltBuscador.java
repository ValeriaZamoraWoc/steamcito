/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.Gets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.Empresas.dtoEmpresa;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuarioComun;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import services.General.Buscador;
import services.General.LocalDateAdapter;

/**
 *
 * @author cacerola
 */
@WebServlet("/buscar")
public class srvltBuscador extends HttpServlet {

    private final Buscador buscador = new Buscador();

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String tipo = request.getParameter("tipo");
        String query = request.getParameter("query");

        if (tipo == null || tipo.isBlank() || query == null || query.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"mensaje\":\"Parámetros requeridos\"}");
            return;
        }

        try {
            Object resultado = null;

            switch (tipo.toLowerCase()) {

                case "usuariomail":
                    resultado = buscador.buscarUsuarioComunPorMail(query);
                    break;

                case "usuarionick":
                    resultado = buscador.buscarUsuarioComunPorNick(query);
                    break;

                case "empresa":
                    resultado = buscador.buscarEmpresaPorNombre(query);
                    break;

                case "juego":
                    resultado = buscador.buscarJuegoPorNombre(query);
                    break;

                case "categoria":
                    resultado = buscador.buscarJuegosPorCategoria(query);
                    break;

                case "clasificacion":
                    resultado = buscador.buscarJuegosPorClasificacion(query);
                    break;
                case "grupofamiliar":
                    resultado = buscador.buscarGrupoFamiliarPorNombre(query);
                    break;

                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("{\"mensaje\":\"Tipo inválido\"}");
                    return;
            }

            if (resultado == null ||
                (resultado instanceof List<?> lista && lista.isEmpty())) {

                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"No encontrado\"}");
                return;
            }

            String json = GSON.toJson(resultado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"mensaje\":\"Error interno\"}");
        }
    }
}
