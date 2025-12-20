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
            int resultado = st.executeUpdate();
            
            
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
            SELECT * FROM biblioteca usuario WHERE mail = ? AND id_juego = ?;
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
        String sqlBoolean = """
            SELECT es_visible FROM biblioteca WHERE mail = ? ;
                            """;
        String sqlCambio ="""
            UPDATE biblioteca SET es_visible = ? WHERE mail = ? ;
                          """;
        
        Boolean tipo=null;
         try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sqlBoolean);
            st.setString(1, biblioteca.getMailUsuario());
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                tipo= rs.getBoolean("es_visible");
                
                PreparedStatement st2 = c.prepareStatement(sqlCambio);
                st2.setBoolean(1, tipo);
                st2.setString(2,biblioteca.getMailUsuario());
                st2.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
