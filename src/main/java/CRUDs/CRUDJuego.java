/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoComentario;
import dtos.Juegos.dtoJuego;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDJuego {
    
    //registrar juego
    public void registrarJuego(dtoJuego juego){
        String sql = """
            INSERT INTO juego 
            (nombre_juego, descripcion, especificaciones, precio,
             id_empresa, id_categoria, id_clasificacion, en_venta, fecha_lanzamiento)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, juego.getNombreJuego());
            st.setString(2, juego.getDescripcion());
            st.setString(3, juego.getEspecificaciones());
            st.setInt(4, juego.getPrecio());
            st.setInt(5, juego.getEmpresa());
            st.setInt(6, juego.getCategoria());
            st.setInt(7, juego.getClasificacion());
            st.setBoolean(8, juego.isEnVenta());
            st.setDate(9, Date.valueOf(juego.getFechaLanzamiento()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //busqueda por título
    public dtoJuego buscarJuegoPorTitulo(String nombreJuego){
        dtoJuego juego = null;

        String sql = """
            SELECT * FROM juego
            WHERE nombre_juego = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombreJuego);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                juego = new dtoJuego();
                juego.setId(rs.getInt("id_juego"));
                juego.setNombreJuego(rs.getString("nombre_juego"));
                juego.setClasificacion(rs.getInt("id_clasificacion"));
                juego.setCategoria(rs.getInt("id_categoria"));
                juego.setEmpresa(rs.getInt("id_empresa"));
                juego.setPrecio(rs.getInt("precio"));
                juego.setEnVenta(rs.getBoolean("en_venta"));
                juego.setDescripcion(rs.getString("descripcion"));
                juego.setEspecificaciones(rs.getString("especificaciones"));
                juego.setUrlImagen(rs.getString("url_imagen"));
                
                Date fecha = rs.getDate("fecha_lanzamiento");
                juego.setFechaLanzamiento(fecha.toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juego;
    }
    
    public dtoJuego buscarJuegoPorId(Integer id){
        dtoJuego juego = null;

        String sql = """
            SELECT *
            FROM juego
            WHERE id_juego = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                juego = new dtoJuego();
                juego.setId(rs.getInt("id_juego"));
                juego.setNombreJuego(rs.getString("nombre_juego"));
                juego.setClasificacion(rs.getInt("id_clasificacion"));
                juego.setCategoria(rs.getInt("id_categoria"));
                juego.setEmpresa(rs.getInt("id_empresa"));
                juego.setPrecio(rs.getInt("precio"));
                juego.setEnVenta(rs.getBoolean("en_venta"));
                juego.setDescripcion(rs.getString("descripcion"));
                juego.setEspecificaciones(rs.getString("especificaciones"));
                juego.setUrlImagen(rs.getString("url_imagen"));
                
                Date fecha = rs.getDate("fecha_lanzamiento");
                juego.setFechaLanzamiento(fecha.toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juego;
    }

    public void actualizarJuego(dtoJuego juego){
        String sql = """
            UPDATE juego SET
            nombre_juego=?, descripcion=?, especificaciones=?, precio=?,
            id_categoria=?, id_clasificacion=?,fecha_lanzamiento =?
            WHERE id_juego = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, juego.getNombreJuego());
            st.setString(2, juego.getDescripcion());
            st.setString(3, juego.getEspecificaciones());
            st.setInt(4, juego.getPrecio());
            st.setInt(5, juego.getCategoria());
            st.setInt(6, juego.getClasificacion());
            st.setDate(7, Date.valueOf(juego.getFechaLanzamiento()));
            st.setInt(8, juego.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cambiarCategoriaJuego(dtoJuego juego, dtoCategoria categoria){
        String sql = """
            UPDATE juego SET
            id_categoria=?
            WHERE id_juego = ? AND id_categoria= ? ;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, categoria.getIdCategoria());
            st.setInt(2, juego.getId());
            st.setInt(8, categoria.getIdCategoria());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    //búsqueda por categoría
    public List<dtoJuego> buscarJuegosPorCategoria(String categoria){
        List<dtoJuego> juegos = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio
            FROM juego j
            INNER JOIN categoria c ON j.id_categoria = c.id_categoria
            WHERE c.nombre_categoria = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, categoria);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoJuego j = new dtoJuego();
                j.setId(rs.getInt("id_juego"));
                j.setNombreJuego(rs.getString("nombre_juego"));
                j.setPrecio(rs.getInt("precio"));
                juegos.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }

    //búsqueda juegos por empresa
    public List<dtoJuego> buscarJuegosPorEmpresa(String empresa){
        List<dtoJuego> juegos = new ArrayList<>();
        String sqlEmpresa = """
                SELECT * FROM juego j 
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                WHERE e.nombre_empresa = ? ;
                """;
        try(Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlEmpresa);
            st.setString(1, empresa);
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                dtoJuego j = new dtoJuego();
                j.setId(resultado.getInt("id_juego"));
                j.setNombreJuego(resultado.getString("nombre_juego"));
                j.setPrecio(resultado.getInt("precio"));
                juegos.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return juegos;
    }
    
    public List<dtoJuego> cargarTodosLosJuegos(){
        List<dtoJuego> juegos = new ArrayList<>();
        String sqlEmpresa = """
                SELECT * FROM juego ;
                """;
        try(Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlEmpresa);
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                dtoJuego j = new dtoJuego();
                j.setId(resultado.getInt("id_juego"));
                j.setNombreJuego(resultado.getString("nombre_juego"));
                j.setPrecio(resultado.getInt("precio"));
                juegos.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return juegos;
    }
    
    public List<dtoComentario> obtenerComentariosJuego(Integer idJuego){
        List<dtoComentario> comentarios= new ArrayList<>();
        String sql= """
            SELECT * FROM comentario WHERE id_juego = ?;
                    """;
        try(Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idJuego);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoComentario comen = new dtoComentario();
                comen.setIdComentario(rs.getInt("id_comentario"));
                comen.setContenido(rs.getString("descripcion"));
                comen.setEsVisible(rs.getBoolean("es_visible"));
                comen.setIdComentrioPadre(rs.getInt("comentario_padre"));
                comen.setIdJuego(rs.getInt("id_juego"));
                comen.setMail(rs.getString("mail"));
                comentarios.add(comen);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }
    
}