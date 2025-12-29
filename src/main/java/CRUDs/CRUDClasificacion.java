/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoCalificacion;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoClasificacion;
import dtos.Juegos.dtoJuego;
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
    public dtoClasificacion buscarClasificacionPorId(Integer id){
        dtoClasificacion clasificacion = null;
        String sql="""
            SELECT * FROM clasificacion WHERE id_clasificacion= ?;
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, id);
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
    
    public List<dtoClasificacion> obtenerClasificaciones(){
        List<dtoClasificacion> clasificaciones = new ArrayList<>();
        String sql = """
            SELECT * FROM clasificacion;
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                dtoClasificacion clasificacion = new dtoClasificacion();
                clasificacion = new dtoClasificacion();
                clasificacion.setIdClasificacion(rs.getInt("id_clasificacion"));
                clasificacion.setNombreCalsificacion(rs.getString("nombre_clasificacion"));
                clasificaciones.add(clasificacion);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clasificaciones;
    }
    
    public List<dtoJuego> buscarJuegosPorClasificacion(String clasificacion) {
        List<dtoJuego> juegos = new ArrayList<>();
        String sql = """
                SELECT j.id_juego, j.nombre_juego, j.id_clasificacion, j.id_categoria, 
                       j.id_empresa, j.precio, j.en_venta, j.descripcion, j.especificaciones, 
                       j.fecha_lanzamiento, j.url_imagen
                FROM juego j 
                INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion 
                WHERE cl.nombre_clasificacion = ?;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, clasificacion); 
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dtoJuego juego = new dtoJuego();
                juego.setId(rs.getInt("id_juego"));
                juego.setNombreJuego(rs.getString("nombre_juego"));
                juego.setClasificacion(rs.getInt("id_clasificacion"));
                juego.setCategoria(rs.getInt("id_categoria"));
                juego.setEmpresa(rs.getInt("id_empresa"));
                juego.setPrecio(rs.getInt("precio"));
                juego.setEnVenta(rs.getBoolean("en_venta"));
                juego.setDescripcion(rs.getString("descripcion"));
                juego.setEspecificaciones(rs.getString("especificaciones"));

                if (rs.getDate("fecha_lanzamiento") != null) {
                    juego.setFechaLanzamiento(rs.getDate("fecha_lanzamiento").toLocalDate());
                }

                juego.setUrlImagen(rs.getString("url_imagen"));
                juegos.add(juego);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
}
