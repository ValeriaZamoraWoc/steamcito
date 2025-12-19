/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoComentario;
import dtos.Usuarios.dtoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDCalificacion {
    
    public void registrarCalificacion(dtoComentario comentario,dtoUsuario usuario,
            int idJuego,Integer calificacion) {

        String sql = """
            INSERT INTO calificacion (mail, id_juego, calificacion, id_comentario)
            VALUES (?, ?, ?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()) {

            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, usuario.getMail());
            st.setInt(2, idJuego);
            st.setString(3, calificacion.toString());

            if (comentario != null) {
                st.setInt(4, comentario.getIdComentario());
            } else {
                st.setNull(4, java.sql.Types.INTEGER);
            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
