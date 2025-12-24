/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoWallet;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioComun;
import dtos.Usuarios.dtoUsuarioDesarrollador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDSistema {

    public List<String[]> gananciasGlobales(){
        List<String[]> ganancias = new ArrayList<>();

        String sql = """
            SELECT j.nombre_juego, SUM(v.monto) total
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            GROUP BY j.nombre_juego;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                String[] fila = new String[2];
                fila[0] = rs.getString("nombre_juego");
                fila[1] = String.valueOf(rs.getInt("total"));
                ganancias.add(fila);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return ganancias;
    }


    //inicio de sesion
    public dtoUsuario inicioSesion(String mail, String contraseña){
        dtoUsuario usuario = null;
        String sqlUsuario = """
            SELECT u.mail, u.nickname, u.contraseña, u.fechaNacimiento, r.nombre_rol
            FROM usuario u
            INNER JOIN rol r ON u.id_rol = r.id_rol
            WHERE u.mail = ? AND u.contraseña = ?;
        """;
        
        try (Connection c = Conexion.obtenerConexion()){ 
            PreparedStatement st = c.prepareStatement(sqlUsuario);
            st.setString(1, mail);
            st.setString(2, contraseña);
            ResultSet resultado = st.executeQuery();

            if(resultado.next()){
                String rol = resultado.getString("nombre_rol");
                usuario = verificarRol(rol);

                usuario.setMail(resultado.getString("mail"));
                usuario.setNickname(resultado.getString("nickname"));
                usuario.setContraseña(resultado.getString("contraseña"));
                usuario.setFechaNacimiento(
                    resultado.getDate("fechaNacimiento").toLocalDate()
                );

                if (usuario instanceof dtoUsuarioComun) {
                    cargarDatosComun((dtoUsuarioComun) usuario);
                }
                else if (usuario instanceof dtoUsuarioDesarrollador) {
                    cargarDatosDesarrollador((dtoUsuarioDesarrollador) usuario);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private void cargarDatosComun(dtoUsuarioComun usuarioComun){
        String sqlDatosExtra = """
                SELECT de.telefono, de.pais, w.saldo FROM datosExtra de 
                INNER JOIN usuario u ON de.mail = u.mail
                INNER JOIN wallet w ON w.mail = u.mail
                WHERE u.mail = ? ;
                """;
        try (Connection c = Conexion.obtenerConexion()){ 
            PreparedStatement st = c.prepareStatement(sqlDatosExtra);
            st.setString(1, usuarioComun.getMail());
            ResultSet resultado = st.executeQuery();

            if(resultado.next()){
                dtoWallet wallet = new dtoWallet();
                wallet.setSaldo(resultado.getInt("saldo"));
                wallet.setMail(usuarioComun.getMail());
                
                usuarioComun.setPais(resultado.getString("pais"));
                usuarioComun.setTelefono(resultado.getInt("telefono"));
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cargarDatosDesarrollador(dtoUsuarioDesarrollador usuarioDev){
        String sql="""
            SELECT * FROM usuario_empresa WHERE mail = ? ;
                   """;
        try (Connection c = Conexion.obtenerConexion()){ 
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, usuarioDev.getMail());
            ResultSet resultado = st.executeQuery();

            if(resultado.next()){
                usuarioDev.setIdEmpresa(resultado.getInt("id_empresa"));
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    private dtoUsuario verificarRol(String rol){
        dtoUsuario user = null;
        switch (rol) {
            case "Admin":
                user = new dtoUsuario();
                break;
            case "Desarrollador":
                user = new dtoUsuarioDesarrollador();
                break;
            default:
                user = new dtoUsuarioComun();
                break;
        }
        return user;
    }

    //búsquedas
    public List<dtoJuego> juegosMasVendidosClasificacion(String clasificacion){
        List<dtoJuego> juegos = new ArrayList<>();

        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio COUNT(v.id_venta) total
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
            WHERE cl.nombre_clasificacion = ?
            GROUP BY j.id_juego
            ORDER BY total DESC;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, clasificacion);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoJuego juego = new dtoJuego();
                juego.setId(rs.getInt("id_juego"));
                juego.setNombreJuego(rs.getString("nombre_juego"));
                juego.setPrecio(rs.getInt("precio"));
                juegos.add(juego);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return juegos;
    }

    public List<dtoJuego> juegosMasVendidosCategoria(String categoria){
        Map<dtoJuego, Integer> mapaJuego = new HashMap<>();

        String sqlJuegos = """
                SELECT j.id_juego, j.nombre_juego, j.precio
                FROM venta v
                INNER JOIN juego j ON v.id_juego = j.id_juego
                INNER JOIN categoria ct ON j.id_categoria = ct.id_categoria
                WHERE ct.nombre_categoria = ? ;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlJuegos);
            st.setString(1, categoria);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                int idJuego = resultado.getInt("id_juego");
                String nombreJuego = resultado.getString("nombre_juego");
                int precio = resultado.getInt("precio");

                dtoJuego j = new dtoJuego();
                j.setId(idJuego);
                j.setNombreJuego(nombreJuego);
                j.setPrecio(precio);

                mapaJuego.put(j, mapaJuego.getOrDefault(j, 0) + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Map.Entry<dtoJuego, Integer>> listaOrdenada = new ArrayList<>(mapaJuego.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue() - a.getValue());

        List<dtoJuego> juegosOrdenados = new ArrayList<>();
        for (Map.Entry<dtoJuego, Integer> entry : listaOrdenada) {
            juegosOrdenados.add(entry.getKey());
        }

        return juegosOrdenados;
    }
    
    public List<dtoJuego> obtenerTodosLosJuegos(){
        List<dtoJuego> juegosOrdenados = new ArrayList<>();
        String sqlJuegos = """
                SELECT * FROM juego;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlJuegos);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                dtoJuego j = new dtoJuego();
                j.setId(rs.getInt("id_juego"));
                j.setNombreJuego(rs.getString("nombre_juego"));
                j.setClasificacion(rs.getInt("id_clasificacion"));
                j.setCategoria(rs.getInt("id_categoria"));
                j.setEmpresa(rs.getInt("id_empresa"));
                j.setPrecio(rs.getInt("precio"));
                j.setEnVenta(rs.getBoolean("en_venta"));
                j.setDescripcion(rs.getString("descripcion"));
                j.setEspecificaciones(rs.getString("especificaciones"));
                j.setUrlImagen(rs.getString("url_imagen"));
                j.setFechaLanzamiento(rs.getObject("fecha_lanzamiento", LocalDate.class));
                juegosOrdenados.add(j);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return juegosOrdenados;
    }
    
    public boolean realizarCompra(String mail, int idJuego, int precio){

        String sqlVenta = """
            INSERT INTO venta (mail, id_juego, monto, fecha)
            VALUES (?, ?, ?, CURDATE());
        """;

        String sqlWallet = """
            UPDATE wallet SET saldo = saldo - ?
            WHERE mail = ? AND saldo >= ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            c.setAutoCommit(false);

            PreparedStatement st1 = c.prepareStatement(sqlWallet);
            st1.setInt(1, precio);
            st1.setString(2, mail);
            st1.setInt(3, precio);

            if(st1.executeUpdate() == 0){
                c.rollback();
                return false;
            }

            PreparedStatement st2 = c.prepareStatement(sqlVenta);
            st2.setString(1, mail);
            st2.setInt(2, idJuego);
            st2.setInt(3, precio);
            st2.executeUpdate();

            c.commit();
            return true;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }



}