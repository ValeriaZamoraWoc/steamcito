/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoJuego;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.General.Conexion;
import models.Juegos.Juego;

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
            SELECT id_juego, nombre_juego, precio, en_venta
            FROM juego
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
                juego.setPrecio(rs.getInt("precio"));
                juego.setEnVenta(rs.getBoolean("en_venta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juego;
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
    
}