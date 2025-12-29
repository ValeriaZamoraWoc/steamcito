/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoCategoria;
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
    
    public dtoCategoria buscarCategoriaPorId(Integer id){
        dtoCategoria categoria = null;
        String sql = """
            SELECT * FROM categoria WHERE id_categoria = ? ;
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, id);
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
    
    public List<dtoCategoria> obtenerCategorias(){
        List<dtoCategoria> categorias = new ArrayList<>();
        String sql = """
            SELECT * FROM categoria;
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                dtoCategoria categoria = new dtoCategoria();
                categoria = new dtoCategoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                categoria.setEdadCategoria(rs.getInt("edad_categoria"));
                categorias.add(categoria);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
    
    public List<dtoJuego> buscarJuegosPorCategoria(String categoria) {
        List<dtoJuego> juegos = new ArrayList<>();
        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.id_clasificacion, j.id_categoria, 
            j.id_empresa, j.precio, j.en_venta, j.descripcion, j.especificaciones, 
            j.fecha_lanzamiento, j.url_imagen
            FROM juego j INNER JOIN categoria c ON j.id_categoria = c.id_categoria 
            WHERE c.nombre_categoria = ?;
                     """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, categoria);
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
