/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Usuarios.dtoGrupoFamiliar;
import dtos.Usuarios.dtoUsuarioComun;
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
public class CRUDGrupoFamiliar {
   
    public void registrarGrupoFamiliar(String nombreGrupo) {

        if (existeGrupoConNombre(nombreGrupo)) {
            return;
        }

        String sqlInsert = """
            INSERT INTO grupoFamiliar (nombre_grupoFamiliar)
            VALUES (?);
        """;

        try (Connection c = Conexion.obtenerConexion()) {

            try (PreparedStatement st = c.prepareStatement(sqlInsert)) {
                st.setString(1, nombreGrupo);
                st.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void agregarPersonaAGrupoFamiliar(int idGrupo, dtoUsuarioComun usuario){
        
        if(!grupoTieneCupo(idGrupo)){
            return;
        }
        
        String sqlUsuario= """
                INSERT INTO grupoFamiliar_usuario (id_grupoFamiliar, mail) 
                VALUES (?, ?);
                           """;
        
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sqlUsuario);
            st.setInt(1, idGrupo);
            st.setString(2, usuario.getMail());
            st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean grupoTieneCupo(int idGrupo){
        String sql = """
            SELECT COUNT(*) AS total
            FROM grupoFamiliar_usuario
            WHERE id_grupoFamiliar = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("total") < 6;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    private boolean existeGrupoConNombre(String nombre){
        String sql = """
            SELECT 1 FROM grupoFamiliar
            WHERE nombre_grupoFamiliar = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public void eliminarPersonaDeGrupo(int idGrupo, String mail){
        String sql = """
            DELETE FROM grupoFamiliar_usuario
            WHERE id_grupoFamiliar = ? AND mail = ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idGrupo);
            st.setString(2, mail);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarGrupoFamiliar(int idGrupo){
        String sql = """
            DELETE FROM grupoFamiliar WHERE id_grupoFamiliar = ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idGrupo);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoGrupoFamiliar obtenerGrupoFamiliar(String nombreGrupo) {

        dtoGrupoFamiliar dto = null;

        String sqlGrupo = """
            SELECT id_grupoFamiliar
            FROM grupoFamiliar
            WHERE nombre_grupoFamiliar = ? ;
        """;

        String sqlIntegrantes = """
            SELECT mail
            FROM grupoFamiliar_usuario
            WHERE nombre_grupoFamiliar = ? ;
        """;

        try (Connection c = Conexion.obtenerConexion()) {

            PreparedStatement stGrupo = c.prepareStatement(sqlGrupo);
            stGrupo.setString(1, nombreGrupo);
            ResultSet rsGrupo = stGrupo.executeQuery();

            if (!rsGrupo.next()) {
                return null;
            }

            dto = new dtoGrupoFamiliar();
            dto.setIdGrupoFamiliar(rsGrupo.getInt("id_grupoFamiliar"));

            PreparedStatement stInt = c.prepareStatement(sqlIntegrantes);
            stInt.setString(1, nombreGrupo);
            ResultSet rsInt = stInt.executeQuery();

            List<String> integrantes = new ArrayList<>();
            while (rsInt.next()) {
                integrantes.add(rsInt.getString("mail"));
            }

            dto.setMailsIntegrantes(integrantes);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public List<String> gruposQueTienenJuego(String mail, int idJuego) {
        List<String> mails = new ArrayList<>();

        String sql = """
            SELECT DISTINCT b.mail
            FROM grupoFamiliar_usuario g1
            INNER JOIN grupoFamiliar_usuario g2 
                   ON g1.id_grupoFamiliar = g2.id_grupoFamiliar
            INNER JOIN biblioteca_juego b 
                   ON b.mail = g2.mail
            WHERE g1.mail = ?
              AND b.id_juego = ?
              AND b.mail <> ?;
        """;

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, mail);
            st.setInt(2, idJuego);
            st.setString(3, mail);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                mails.add(rs.getString("mail"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mails;
    }


    public List<dtoGrupoFamiliar> obtenerGruposPorUsuario(String mail) {
        List<dtoGrupoFamiliar> grupos = new ArrayList<>();

        String sql = """
            SELECT gf.id_grupoFamiliar, gf.nombre_grupoFamiliar
            FROM grupoFamiliar gf
            INNER JOIN grupoFamiliar_usuario gfu ON gf.id_grupoFamiliar = gfu.id_grupoFamiliar
            WHERE gfu.mail = ?
        """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dtoGrupoFamiliar grupo = new dtoGrupoFamiliar();
                grupo.setIdGrupoFamiliar(rs.getInt("id_grupoFamiliar"));
                grupo.setNombreGrupo(rs.getString("nombre_grupoFamiliar"));
                grupos.add(grupo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grupos;
    }

    public List<dtoUsuarioComun> obtenerIntegrantesGrupo(int idGrupo) {
        List<dtoUsuarioComun> integrantes = new ArrayList<>();

        String sql = """
            SELECT u.mail, u.nickname, de.telefono
            FROM grupoFamiliar_usuario gfu
            INNER JOIN usuario u ON gfu.mail = u.mail
            INNER JOIN datosExtra de ON gfu.mail= de.mail
            WHERE gfu.id_grupoFamiliar = ?
        """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idGrupo);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dtoUsuarioComun usuario = new dtoUsuarioComun();
                usuario.setMail(rs.getString("mail"));
                usuario.setNickname(rs.getString("nickname"));
                usuario.setTelefono(rs.getInt("telefono"));
                integrantes.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return integrantes;
    }
    
    public List<String[]> obtenerPrestadosMasJugados(String mail) {
        List<String[]> juegos = new ArrayList<>();
        String sql = """
            SELECT j.nombre_juego, 
                    SUM(DATEDIFF(p.fecha_devolucion, p.fecha_prestamo) + 1) as dias_totales, 
                    j.url_imagen
            FROM prestamo p
            INNER JOIN juego j ON p.id_juego = j.id_juego
            WHERE p.mail_prestamista = ? AND p.devuelto = 1
            GROUP BY j.id_juego, j.nombre_juego, j.url_imagen
            ORDER BY dias_totales DESC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[3];
                fila[0] = resultado.getString("nombre_juego");
                fila[1] = String.valueOf(resultado.getInt("dias_totales"));
                fila[2] = resultado.getString("url_imagen");
                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
        
    public List<String[]> obtenerMejoresCalificadosPrestados(String mail) {
        List<String[]> juegos = new ArrayList<>();
        String sql = """
                SELECT DISTINCT j.id_juego, j.nombre_juego, AVG(c.calificacion) as promedio, j.url_imagen
                FROM prestamo p
                INNER JOIN juego j ON p.id_juego = j.id_juego
                INNER JOIN calificacion c ON j.id_juego = c.id_juego
                WHERE p.mail_prestamista = ?
                GROUP BY j.id_juego, j.nombre_juego, j.url_imagen
                ORDER BY promedio DESC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[4];
                fila[0] = String.valueOf(resultado.getInt("id_juego"));
                fila[1] = resultado.getString("nombre_juego");
                fila[2] = String.format("%.2f", resultado.getDouble("promedio"));
                fila[3] = resultado.getString("url_imagen");
                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }

}
