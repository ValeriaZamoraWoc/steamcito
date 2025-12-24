/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.JuegosDinamica;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author cacerola
 */
@WebServlet("/uploads/juegos/*")
public class srvltImagenes extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String pathInfo = req.getPathInfo(); // /juego_1_123.jpg

        if (pathInfo == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String home = System.getProperty("user.home");
        File archivo = new File(
            home + File.separator + "uploads" + File.separator + "juegos",
            pathInfo
        );

        if (!archivo.exists()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Detectar tipo (mejor que hardcodear jpg)
        String contentType = Files.probeContentType(archivo.toPath());
        resp.setContentType(contentType != null ? contentType : "image/jpeg");

        resp.setHeader("Cache-Control", "public, max-age=86400");

        Files.copy(archivo.toPath(), resp.getOutputStream());
    }
}

