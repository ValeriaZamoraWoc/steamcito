/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ReportesAdminJasperReports;

import dtos.ReportesJasperReports.Admin.ReporteGananciasGlobalesAdminDTO;
import dtos.ReportesJasperReports.Admin.ReporteGananciasPorEmpresaAdminDTO;
import dtos.ReportesJasperReports.Admin.ReporteJuegosMasVendidosAdminDTO;
import dtos.ReportesJasperReports.Admin.ReporteJugadoresConMasJuegosAdminDTO;
import dtos.ReportesJasperReports.Admin.ReporteMejoresCalificadosAdminDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class ReportesAdminJasperService {


    public List<ReporteGananciasGlobalesAdminDTO> gananciasGlobales() {

        List<ReporteGananciasGlobalesAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.nombre_juego,
                   SUM(v.dinero_comision + v.dinero_empresa) AS total_general
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            GROUP BY j.nombre_juego;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                ReporteGananciasGlobalesAdminDTO dto =
                        new ReporteGananciasGlobalesAdminDTO();

                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setTotalGeneral(rs.getInt("total_general"));

                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReporteGananciasPorEmpresaAdminDTO> gananciasPorEmpresa() {

        List<ReporteGananciasPorEmpresaAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT e.nombre_empresa,
                   SUM(v.dinero_empresa) AS total_ganancia
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            GROUP BY e.nombre_empresa;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                ReporteGananciasPorEmpresaAdminDTO dto =
                        new ReporteGananciasPorEmpresaAdminDTO();

                dto.setNombreEmpresa(rs.getString("nombre_empresa"));
                dto.setTotalGanancia(rs.getDouble("total_ganancia"));

                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReporteJugadoresConMasJuegosAdminDTO> jugadoresConMasJuegos() {

        List<ReporteJugadoresConMasJuegosAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT u.nickname, COUNT(bj.id_juego) AS cantidad_juegos
            FROM biblioteca_juego bj
            INNER JOIN usuario u ON bj.mail = u.mail
            GROUP BY u.nickname, u.mail
            ORDER BY cantidad_juegos DESC;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                ReporteJugadoresConMasJuegosAdminDTO dto =
                        new ReporteJugadoresConMasJuegosAdminDTO();

                dto.setNickname(rs.getString("nickname"));
                dto.setCantidadJuegos(rs.getInt("cantidad_juegos"));

                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<ReporteMejoresCalificadosAdminDTO> mejoresCalificadosCategoria(String categoria) {

        List<ReporteMejoresCalificadosAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio,
                   AVG(CAST(c.calificacion AS UNSIGNED)) AS promedio
            FROM calificacion c
            INNER JOIN juego j ON c.id_juego = j.id_juego
            INNER JOIN categoria ct ON j.id_categoria = ct.id_categoria
            WHERE ct.nombre_categoria = ?
            GROUP BY j.id_juego, j.nombre_juego, j.precio
            ORDER BY promedio DESC;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, categoria);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteMejoresCalificadosAdminDTO dto =
                        new ReporteMejoresCalificadosAdminDTO();

                dto.setIdJuego(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setPromedioCalificacion(rs.getDouble("promedio"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public List<ReporteMejoresCalificadosAdminDTO> mejoresCalificadosClasificacion(String clasificacion) {

        List<ReporteMejoresCalificadosAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio,
                   AVG(CAST(c.calificacion AS UNSIGNED)) AS promedio
            FROM calificacion c
            INNER JOIN juego j ON c.id_juego = j.id_juego
            INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
            WHERE cl.nombre_clasificacion = ?
            GROUP BY j.id_juego, j.nombre_juego, j.precio
            ORDER BY promedio DESC;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, clasificacion);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteMejoresCalificadosAdminDTO dto =
                        new ReporteMejoresCalificadosAdminDTO();

                dto.setIdJuego(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setPromedioCalificacion(rs.getDouble("promedio"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public List<ReporteJuegosMasVendidosAdminDTO> juegosMasVendidosCategoria(String categoria) {

        List<ReporteJuegosMasVendidosAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio,
                   COUNT(v.id_venta) AS total_ventas
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            INNER JOIN categoria ct ON j.id_categoria = ct.id_categoria
            WHERE ct.nombre_categoria = ?
            GROUP BY j.id_juego, j.nombre_juego, j.precio
            ORDER BY total_ventas DESC;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, categoria);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteJuegosMasVendidosAdminDTO dto =
                        new ReporteJuegosMasVendidosAdminDTO();

                dto.setIdJuego(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setTotalVentas(rs.getInt("total_ventas"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
   
    public List<ReporteJuegosMasVendidosAdminDTO> juegosMasVendidosClasificacion(String clasificacion) {

        List<ReporteJuegosMasVendidosAdminDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio,
                   COUNT(v.id_venta) AS total_ventas
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
            WHERE cl.nombre_clasificacion = ?
            GROUP BY j.id_juego, j.nombre_juego, j.precio
            ORDER BY total_ventas DESC;
        """;

        try (Connection c = Conexion.obtenerConexion();
             PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, clasificacion);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteJuegosMasVendidosAdminDTO dto =
                        new ReporteJuegosMasVendidosAdminDTO();

                dto.setIdJuego(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setTotalVentas(rs.getInt("total_ventas"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
