/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoCategoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDCategoria {
    
    //lo de edades
    
    public void registrarCategoria(String nombreCategoria, Integer edad){
        String sql="""
            INSERT INTO categoria (nombre_categoria, edad_categoria) VALUES ( ?, ?);
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombreCategoria);
            st.setInt(2, edad);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarCategoria(int idCategoria){
        String sql ="""
            DELETE FROM categoria WHERE id_categoria = ? ;
                    """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idCategoria);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void modificarEdadCategoria(Integer idCategoria, Integer edadNueva){
        String sql ="""
            UPDATE categoria SET edad_categoria = ? WHERE id_categoria = ?;
                    """ ;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, edadNueva);
            st.setInt(2, idCategoria);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoCategoria buscarCategoriaPorNombre(String nombre){
        dtoCategoria categoria = null;
        String sql = """
            SELECT * FROM categoria WHERE nombre_categoria = ? ;
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombre);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                categoria = new dtoCategoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setEdadCategoria(rs.getInt("edad_categoria"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }
    
}
