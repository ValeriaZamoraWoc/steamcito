/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import controllers.Registros.TipoUsuario;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioComun;
import dtos.Usuarios.dtoUsuarioDesarrollador;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import services.General.Conexion;


/**
 *
 * @author cacerola
 */
public class CRUDUsuario {

    //registro de usuarios
    public void registrarUsuario(dtoUsuario dto, TipoUsuario tipo) throws SQLException {
        

        if (existeUsuario(dto.getMail())) {
            System.out.println("simon");
            return;
        }

        int idRol = obtenerIdRol(tipo.name());
        if (idRol == -1) {
            System.out.println("simon2");
            return;
        }

        String sqlUsuario = """
            INSERT INTO usuario (mail, nickname, id_rol, contraseña, fechaNacimiento)
            VALUES (?, ?, ?, ?, ?);
        """;

        String sqlDatosExtra = """
            INSERT INTO datosExtra (mail, telefono, pais)
            VALUES (?, ?, ?);
        """;

        String sqlWallet = """
            INSERT INTO wallet (saldo, mail)
            VALUES (?, ?);
        """;
        
        String sqlUsuarioEmpresa = """
            INSERT INTO usuario_empresa (id_empresa, mail)
            VALUES (?, ?);
        """;
        
        String sqlBiblioteca ="""
            INSERT INTO biblioteca (mail, es_publica) 
            VALUES (?, ?);
        """;

        try (Connection c = Conexion.obtenerConexion()) {

            c.setAutoCommit(false);

            // Usuario base
            try (PreparedStatement st = c.prepareStatement(sqlUsuario)) {
                st.setString(1, dto.getMail());
                st.setString(2, dto.getNickname());
                st.setInt(3, idRol);
                st.setString(4, dto.getContraseña());
                st.setDate(5, Date.valueOf(dto.getFechaNacimiento()));
                st.executeUpdate();
            }

            if(tipo== TipoUsuario.Desarrollador && dto instanceof dtoUsuarioDesarrollador u){
                try (PreparedStatement st = c.prepareStatement(sqlUsuarioEmpresa)){
                    st.setInt(1, u.getIdEmpresa());
                    st.setString(2, u.getMail());
                    st.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // Solo si es usuario común
            if (tipo == TipoUsuario.Comun && dto instanceof dtoUsuarioComun u) {

                try (PreparedStatement st = c.prepareStatement(sqlDatosExtra)) {
                    st.setString(1, u.getMail());
                    st.setInt(2, u.getTelefono());
                    st.setString(3, u.getPais());
                    st.executeUpdate();
                }

                try (PreparedStatement st = c.prepareStatement(sqlWallet)) {
                    st.setInt(1, 0); 
                    st.setString(2, u.getMail());
                    st.executeUpdate();
                    
                }
                
                try (PreparedStatement st = c.prepareStatement(sqlBiblioteca)) {
                    st.setString(1, u.getMail());
                    st.setBoolean(2, true);
                    st.executeUpdate();
                }
            }

            c.commit();


        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private int obtenerIdRol(String nombreRol) {
        String sql = "SELECT id_rol FROM rol WHERE nombre_rol = ?";

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombreRol); 
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_rol");
            } else {
                return -1; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    //selects
    public dtoUsuarioComun buscarUsuarioComunPorNickname(String nickname){
        dtoUsuarioComun dto = null;

        String sql = """
            SELECT u.mail, u.nickname, u.contraseña, u.fechaNacimiento,
                   de.pais, de.telefono,
                   w.saldo,
                   b.es_publica
            FROM usuario u
            INNER JOIN datosExtra de ON de.mail = u.mail
            INNER JOIN wallet w ON w.mail = u.mail
            INNER JOIN biblioteca b ON b.mail = u.mail
            WHERE u.nickname = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nickname);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                dto = new dtoUsuarioComun();
                dto.setMail(rs.getString("mail"));
                dto.setNickname(rs.getString("nickname"));
                dto.setContraseña(rs.getString("contraseña"));
                Date fecha = rs.getDate("fechaNacimiento");
                if (fecha != null) {
                    dto.setFechaNacimiento(fecha.toLocalDate());
                }
                dto.setTelefono(rs.getInt("telefono"));
                dto.setPais(rs.getString("pais"));
                
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return dto;
    }
    
    public dtoUsuarioComun buscarUsuarioComunPorMail(String mail){
        dtoUsuarioComun dto = null;

        String sql = """
            SELECT u.mail, u.nickname, u.contraseña, u.fechaNacimiento,
                   de.pais, de.telefono,
                   w.saldo,
                   b.es_publica
            FROM usuario u
            INNER JOIN datosExtra de ON de.mail = u.mail
            INNER JOIN wallet w ON w.mail = u.mail
            INNER JOIN biblioteca b ON b.mail = u.mail
            WHERE u.mail = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                dto = new dtoUsuarioComun();
                dto.setMail(rs.getString("mail"));
                dto.setNickname(rs.getString("nickname"));
                dto.setContraseña(rs.getString("contraseña"));
                Date fecha = rs.getDate("fechaNacimiento");
                if (fecha != null) {
                    dto.setFechaNacimiento(fecha.toLocalDate());
                }
                dto.setTelefono(rs.getInt("telefono"));
                dto.setPais(rs.getString("pais"));
                
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return dto;
    }

    public dtoUsuarioDesarrollador buscarUsuarioDevPorMail(String mail){
        dtoUsuarioDesarrollador dto = null;

        String sql = """
            SELECT u.mail, u.nickname, u.contraseña, u.fechaNacimiento,
            ue.id_empresa FROM usuario u
            INNER JOIN usuario_empresa ue ON u.mail = ue.mail
            WHERE u.mail = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                dto = new dtoUsuarioDesarrollador();
                dto.setMail(rs.getString("mail"));
                dto.setNickname(rs.getString("nickname"));
                dto.setContraseña(rs.getString("contraseña"));
                dto.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                dto.setIdEmpresa(rs.getInt("id_empresa"));
                
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return dto;
    }
    
    public List<dtoJuego> obtenerBibliotecaUsuario(String mail) throws SQLException {

        List<dtoJuego> juegos = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.id_clasificacion, j.id_categoria, j.id_empresa, j.precio, j.en_venta, 
                     j.descripcion, j.especificaciones, j.fecha_lanzamiento, j.url_imagen,bj.instalado
            FROM biblioteca_juego bj
            INNER JOIN juego j ON bj.id_juego = j.id_juego
            WHERE bj.mail = ? ;
        """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dtoJuego dto = new dtoJuego();
                dto.setId(rs.getInt("id_juego"));
                dto.setNombreJuego(rs.getString("nombre_juego"));
                dto.setClasificacion(rs.getInt("id_clasificacion"));
                dto.setCategoria(rs.getInt("id_categoria"));
                dto.setEmpresa(rs.getInt("id_empresa"));
                dto.setPrecio(rs.getInt("precio"));
                dto.setEnVenta(rs.getBoolean("en_venta"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setEspecificaciones(rs.getString("especificaciones"));
                Date fecha = rs.getDate("fecha_lanzamiento");
                dto.setFechaLanzamiento(fecha.toLocalDate());
                dto.setUrlImagen(rs.getString("url_imagen"));
                dto.setInstalado(rs.getBoolean("instalado"));
                juegos.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return juegos;
    }

    private boolean existeUsuario(String mail){
        String sql = "SELECT 1 FROM usuario WHERE mail = ?";
        try(Connection c = Conexion.obtenerConexion()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, mail);
            return ps.executeQuery().next();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

}