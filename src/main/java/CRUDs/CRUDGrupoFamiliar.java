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

    public List<Integer> gruposQueTienenJuego(String mail, int idJuego) {
        List<Integer> ids = new ArrayList<>();

        String sql = """
            SELECT id_grupoFamiliar FROM grupoFamiliar_usuario
            WHERE mail = ? AND id_grupoFamiliar IN ( SELECT id_grupoFamiliar FROM grupoFamiliar_usuario 
            INNER JOIN biblioteca_juego ON grupoFamiliar_usuario.mail = biblioteca_juego.mail
            WHERE id_juego = ? AND grupoFamiliar_usuario.mail != ?
                     """;
        
        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, mail);
            st.setInt(2, idJuego);
            st.setString(3, mail);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt(1)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }


}
