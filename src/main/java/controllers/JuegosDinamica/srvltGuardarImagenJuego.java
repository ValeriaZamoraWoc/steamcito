/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.JuegosDinamica;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import services.Registros.RegistrarImagenes;

/**
 *
 * @author cacerola
 */
@WebServlet("/registrarImagenJuego")
@MultipartConfig
public class srvltGuardarImagenJuego extends HttpServlet{
    
    private RegistrarImagenes servicio = new RegistrarImagenes();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().write("SERVLET VIVO");
        response.setContentType("application/json;charset=UTF-8");

        String idJuegoStr = request.getParameter("idJuego");
        Part imagen = request.getPart("imagen");

        if (idJuegoStr == null || imagen == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int idJuego = Integer.parseInt(idJuegoStr);

        // 1. Ruta física
        String rutaBase = getServletContext().getRealPath("/uploads/juegos/");
        File carpeta = new File(rutaBase);
        if (!carpeta.exists()) carpeta.mkdirs();

        // 2. Nombre único
        String nombreArchivo = "juego_" + idJuego + "_" + imagen.getSubmittedFileName();

        // 3. Guardar archivo
        imagen.write(rutaBase + nombreArchivo);

        // 4. URL pública
        String url = request.getScheme() + "://" +
             request.getServerName() + ":" +
             request.getServerPort() +
             request.getContextPath() +
             "/uploads/juegos/" + nombreArchivo;
        
        try {
            servicio.registrarImagenJuego(idJuego, url);
            response.getWriter().write("""
                { "exito": true, "url": "%s" }
            """.formatted(url));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    
}
