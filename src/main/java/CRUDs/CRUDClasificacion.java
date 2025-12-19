/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoCalificacion;
import dtos.Juegos.dtoClasificacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDClasificacion {
    
    //géneros de juego
    
    public void registrarClasificacion(dtoClasificacion clasificacion){
        String sql = """
            INSERT INTO clasificacion (nombre_clasificacion)
            VALUES (?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, clasificacion.getNombreCalsificacion());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarClasificacion(dtoClasificacion clasificacion){
        String sql ="""
            DELETE FROM clasificacion WHERE id_clasificacion = ? ;
                    """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, clasificacion.getIdClasificacion());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoClasificacion buscarClasificacionPorNombre(String nombre){
        dtoClasificacion clasificacion = null;
        String sql="""
            SELECT * FROM clasificacion WHERE nombre_clasificacion= ?;
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                clasificacion = new dtoClasificacion();
                clasificacion.setIdClasificacion(rs.getInt("id_clasificacion"));
                clasificacion.setNombreCalsificacion(rs.getString("nombre_clasificacion"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clasificacion;
    }
}
