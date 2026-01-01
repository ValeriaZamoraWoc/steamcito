/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ReportesAdminJasperReports;

import dtos.Empresas.dtoEmpresa;
import dtos.ReportesJasperReports.Empresa.ReporteComentariosEmpresaDTO;
import dtos.ReportesJasperReports.Empresa.ReporteJuegosCalificadosEmpresaDTO;
import dtos.ReportesJasperReports.Empresa.ReporteVentasEmpresaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class ReportesEmpresaJasperService {

    public List<ReporteVentasEmpresaDTO> obtenerVentasEmpresa(Integer idEmpresa) {
        Map<String, ReporteVentasEmpresaDTO> acumulado = new HashMap<>();

        String sql = """
            SELECT v.dinero_comision, v.dinero_empresa,
                   j.nombre_juego, c.porcentaje
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            INNER JOIN comision_empresa ce ON e.id_empresa = ce.id_empresa
            INNER JOIN comision c ON ce.id_comision = c.id_comision
            WHERE e.id_empresa = ?;
        """;

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, idEmpresa);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String juego = rs.getString("nombre_juego");
                double montoVenta = rs.getInt("dinero_comision") + rs.getInt("dinero_empresa");
                int porcentaje = rs.getInt("porcentaje");

                double comisionVenta = (montoVenta * porcentaje) / 100;
                double gananciaNetaVenta = montoVenta - comisionVenta;

                ReporteVentasEmpresaDTO dto = acumulado.getOrDefault(juego, new ReporteVentasEmpresaDTO());

                if (!acumulado.containsKey(juego)) {
                    dto.setNombreJuego(juego);
                    dto.setMontoTotal(0);
                    dto.setPorcentajeComision(porcentaje);
                    dto.setComision(0);
                    dto.setGananciaNeta(0);
                    acumulado.put(juego, dto);
                }

                dto.setMontoTotal(montoVenta);
                dto.setComision(dto.getComision());
                dto.setGananciaNeta(dto.getGananciaNeta() + gananciaNetaVenta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al procesar ventas de empresa", e);
        }

        return new ArrayList<>(acumulado.values());
    }

    public List<ReporteJuegosCalificadosEmpresaDTO> obtenerJuegosMejorCalificadosEmpresa(Integer idEmpresa) {

        return obtenerJuegosCalificados(idEmpresa, "DESC");
    }

    public List<ReporteJuegosCalificadosEmpresaDTO> obtenerJuegosPeorCalificadosEmpresa(Integer idEmpresa) {

        return obtenerJuegosCalificados(idEmpresa, "ASC");
    }

    public List<ReporteJuegosCalificadosEmpresaDTO> obtenerTop5(Integer idEmpresa) {

        List<ReporteJuegosCalificadosEmpresaDTO> lista =
                obtenerJuegosMejorCalificadosEmpresa(idEmpresa);

        return lista.subList(0, Math.min(lista.size(), 5));
    }
    
    public List<ReporteComentariosEmpresaDTO> obtenerComentariosMejorCalificadosEmpresa(Integer idEmpresa) {

        List<ReporteComentariosEmpresaDTO> lista = new ArrayList<>();

        String sql = """
            SELECT com.id_comentario, com.descripcion, j.nombre_juego,
                   com.mail, AVG(cal.calificacion) AS promedio
            FROM comentario com
            INNER JOIN juego j ON com.id_juego = j.id_juego
            INNER JOIN calificacion cal ON com.id_comentario = cal.id_comentario
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            WHERE e.id_empresa = ?
              AND com.comentario_padre IS NULL
              AND com.es_visible = 1
            GROUP BY com.id_comentario, com.descripcion,
                     j.nombre_juego, com.mail
            ORDER BY promedio DESC;
        """;

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, idEmpresa);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteComentariosEmpresaDTO dto =
                        new ReporteComentariosEmpresaDTO();

                String desc = rs.getString("descripcion");

                dto.setIdComentario(rs.getInt("id_comentario"));
                dto.setDescripcion(
                        desc.length() > 50 ? desc.substring(0, 47) + "..." : desc
                );
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setMailUsuario(rs.getString("mail"));
                dto.setPromedioCalificacion(rs.getDouble("promedio"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public dtoEmpresa buscarEmpresaPorId(Integer id){
        dtoEmpresa empresa = null;
        String sql = """
            SELECT id_empresa, nombre_empresa
            FROM empresa
            WHERE id_empresa = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                empresa = new dtoEmpresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresa;
    }
    
    private List<ReporteJuegosCalificadosEmpresaDTO> obtenerJuegosCalificados(Integer idEmpresa, String orden) {

        List<ReporteJuegosCalificadosEmpresaDTO> lista = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego,
                   AVG(c.calificacion) AS promedio,
                   j.precio, img.url
            FROM juego j
            INNER JOIN calificacion c ON j.id_juego = c.id_juego
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            LEFT JOIN imagen img ON j.url_imagen = img.url
            WHERE e.id_empresa = ?
            GROUP BY j.id_juego, j.nombre_juego, j.precio, img.url
            ORDER BY promedio %s;
        """.formatted(orden);

        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, idEmpresa);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                ReporteJuegosCalificadosEmpresaDTO dto =
                        new ReporteJuegosCalificadosEmpresaDTO();

                dto.setIdJuego(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setPromedioCalificacion(rs.getDouble("promedio"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setUrlImagen(rs.getString("url"));

                lista.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
