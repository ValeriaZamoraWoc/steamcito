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
import java.nio.file.Paths;
import services.Registros.RegistrarImagenes;

/**
 *
 * @author cacerola
 */
@WebServlet("/guardarImagenJuego")
@MultipartConfig
public class srvltGuardarImagenJuego extends HttpServlet {

    private RegistrarImagenes servicio = new RegistrarImagenes();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            String idJuegoStr = req.getParameter("idJuego");
            Part part = req.getPart("imagen");

            if (idJuegoStr == null || part == null || part.getSize() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            int idJuego = Integer.parseInt(idJuegoStr);

            String home = System.getProperty("user.home");
            File carpeta = new File(home + File.separator + "uploads" + File.separator + "juegos");

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String nombreArchivo = "juego_" + idJuego + "_" + System.currentTimeMillis() + ".jpg";
            File archivoFinal = new File(carpeta, nombreArchivo);
            part.write(archivoFinal.getAbsolutePath());

            String url =
                req.getScheme() + "://" +
                req.getServerName() + ":" +
                req.getServerPort() +
                req.getContextPath() +
                "/uploads/juegos/" + nombreArchivo;


            servicio.registrarImagenJuego(idJuego, url);

            resp.setContentType("application/json");
            resp.getWriter().write("""
                {
                  "exito": true,
                  "url": "%s"
                }
            """.formatted(url));

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

