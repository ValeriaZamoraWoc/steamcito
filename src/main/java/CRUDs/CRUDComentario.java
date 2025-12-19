/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoComentario;
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
public class CRUDComentario {
    
    public void registrarComentario(dtoComentario comentario, Integer comentarioPadre){
        String sql = """
            INSERT INTO comentario (descripcion, es_visible, comentario_padre)
            VALUES (?, ?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, comentario.getContenido());
            st.setBoolean(2, comentario.isEsVisible());

            if(comentarioPadre != null){
                st.setInt(3, comentarioPadre);
            } else {
                st.setNull(3, java.sql.Types.INTEGER);
            }

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void ocultarComentario(dtoComentario comentario){
        String sql = """
            UPDATE comentario SET es_visible = FALSE
            WHERE id_comentario = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, comentario.getIdComentario());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<dtoComentario> obtenerComentariosVisibles(){
        List<dtoComentario> comentarios = new ArrayList<>();

        String sql = """
            SELECT id_comentario, descripcion, es_visible
            FROM comentario
            WHERE es_visible = TRUE AND comentario_padre IS NULL;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoComentario comentario = new dtoComentario();
                comentario.setIdComentario(rs.getInt("id_comentario"));
                comentario.setContenido(rs.getString("descripcion"));
                comentario.setEsVisible(rs.getBoolean("es_visible"));
                comentarios.add(comentario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    
    public List<dtoComentario> obtenerRespuestas(int idComentario){
        List<dtoComentario> respuestas = new ArrayList<>();

        String sql = """
            SELECT id_comentario, descripcion, es_visible
            FROM comentario
            WHERE comentario_padre = ? AND es_visible = TRUE;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idComentario);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoComentario comentario = new dtoComentario();
                comentario.setIdComentario(rs.getInt("id_comentario"));
                comentario.setContenido(rs.getString("descripcion"));
                comentario.setEsVisible(rs.getBoolean("es_visible"));
                respuestas.add(comentario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respuestas;
    }

}
