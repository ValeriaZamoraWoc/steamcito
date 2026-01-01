/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoBiblioteca;
import dtos.ObjetosUsuario.dtoBibliotecaJuego;
import dtos.Usuarios.dtoUsuario;
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
public class CRUDBiblioteca {
    
    public dtoBiblioteca buscarBibliotecaPorMail(String mail){
        dtoBiblioteca biblioteca = null;
        String sql = """
            SELECT * FROM biblioteca WHERE mail = ? ;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                biblioteca = new dtoBiblioteca();
                biblioteca.setEsPublica(rs.getBoolean("es_publica"));
                biblioteca.setMailUsuario(rs.getString("mail"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return biblioteca;
        
    }
    
    public void registrarBiblioteca(dtoBiblioteca biblioteca){
        String sql = """
            INSERT INTO biblioteca (mail, es_publica)
            VALUES (?, TRUE);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, biblioteca.getMailUsuario());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void registrarJuegoEnBiblioteca(dtoJuego juego, dtoBiblioteca biblioteca){
        String sql = """
            INSERT INTO biblioteca_juego (mail, id_juego, instalado)
            VALUES (?, ?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, biblioteca.getMailUsuario());
            st.setInt(2, juego.getId());
            st.setBoolean(3, false);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void instalarJuego(dtoUsuario usuario, dtoJuego juego){
        String sqlJuego="""
            UPDATE biblioteca_juego SET instalado = TRUE WHERE id_juego = ? AND mail= ? ;
                        """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sqlJuego);
            st.setInt(1, juego.getId());
            st.setString(2, usuario.getMail());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void desinstalarJuego(String mail,int idJuego){
        String sqlJuego="""
            UPDATE biblioteca_juego SET instalado = FALSE WHERE id_juego = ? AND mail= ? ;
                        """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sqlJuego);
            st.setInt(1, idJuego);
            st.setString(2, mail);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarJuegoDeBiblioteca(String mail, int idJuego){
        String sql = """
            DELETE FROM biblioteca_juego
            WHERE mail = ? AND id_juego = ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            st.setInt(2, idJuego);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean buscarJuegoEnBiblioteca(dtoBiblioteca biblioteca, dtoJuego juego){
        String sql = """
            SELECT * FROM biblioteca_juego WHERE mail = ? AND id_juego = ?;
                     """;
        
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, biblioteca.getMailUsuario());
            st.setInt(2, juego.getId());
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void cambiarVisibilidadBiblioteca(dtoBiblioteca biblioteca){
        /*String sqlBoolean = """
            SELECT es_visible FROM biblioteca WHERE mail = ? ;
                            """;*/
        String sqlCambio ="""
            UPDATE biblioteca SET es_publica = ? WHERE mail = ? ;
                          """;
        
         try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sqlCambio);
            st.setBoolean(1, !biblioteca.getEsPublica());
            st.setString(2,biblioteca.getMailUsuario());
            st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean obtenerVisibilidadBiblioteca(String mail){
        String sql ="""
            SELECT es_publica FROM biblioteca WHERE mail = ? ;
                          """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                return rs.getBoolean("es_publica");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public List<String[]> obtenerMejoresCalificadosBiblioteca(String mail) {
        List<String[]> juegos = new ArrayList<>();
        String sql = """
                SELECT j.id_juego, j.nombre_juego, AVG(c.calificacion) as promedio, j.url_imagen
                FROM juego j
                INNER JOIN biblioteca_juego bj ON j.id_juego = bj.id_juego
                INNER JOIN calificacion c ON j.id_juego = c.id_juego
                WHERE bj.mail = ?
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
                fila[2] = String.valueOf(resultado.getDouble("promedio"));
                fila[3] = resultado.getString("url_imagen");
                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
    
    public List<String[]> obtenerCalificacionesPersonales(String mail) {
        List<String[]> juegos = new ArrayList<>();
        String sql = """
                SELECT j.id_juego, j.nombre_juego, c.calificacion, j.url_imagen
                FROM juego j
                INNER JOIN calificacion c ON j.id_juego = c.id_juego
                WHERE c.mail = ?
                ORDER BY c.calificacion DESC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[4];
                fila[0] = String.valueOf(resultado.getInt("id_juego"));
                fila[1] = resultado.getString("nombre_juego");
                fila[2] = resultado.getString("calificacion");
                fila[3] = resultado.getString("url_imagen");
                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
    
    public List<String[]> obtenerClasificacionesFavoritas(String mail) {
        List<String[]> clasificaciones = new ArrayList<>();

        String sql = """
                SELECT cl.nombre_clasificacion, COUNT(j.id_juego) as cantidad
                FROM juego j
                INNER JOIN biblioteca_juego bj ON j.id_juego = bj.id_juego
                INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
                WHERE bj.mail = ?
                GROUP BY cl.id_clasificacion, cl.nombre_clasificacion
                ORDER BY cantidad DESC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[2];
                fila[0] = resultado.getString("nombre_clasificacion");
                fila[1] = String.valueOf(resultado.getInt("cantidad"));

                clasificaciones.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clasificaciones;
    }
        
}
