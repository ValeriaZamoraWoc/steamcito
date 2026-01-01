/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Empresas.dtoComision;
import dtos.Empresas.dtoEmpresa;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
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
public class CRUDEmpresa {
    
    //registrar
    public void registrarEmpresa(dtoEmpresa empresa){
        String sql = """
            INSERT INTO empresa (nombre_empresa, descripcion)
            VALUES (?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, empresa.getNombreEmpresa());
            st.setString(2, empresa.getDescripcion());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void asignarComisionEmpresa(dtoEmpresa empresa, dtoComision comision){
        String sql = """
            INSERT INTO comision_empresa (id_empresa, id_comision)
            VALUES (?, ?);
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, empresa.getIdEmpresa());
            st.setInt(2, comision.getIdComision());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoEmpresa buscarEmpresaPorNombre(String nombreEmpresa){
        dtoEmpresa empresa = null;
        String sql = """
            SELECT id_empresa, nombre_empresa
            FROM empresa
            WHERE nombre_empresa = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, nombreEmpresa);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                empresa = new dtoEmpresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresa;
    }

    public dtoEmpresa buscarEmpresaPorId(Integer id){
        dtoEmpresa empresa = null;
        String sql = """
            SELECT id_empresa, nombre_empresa
            FROM empresa
            WHERE id_empresa = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                empresa = new dtoEmpresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setNombreEmpresa(rs.getString("nombre_empresa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresa;
    }
    
    public List<dtoJuego> obtenerCatalogoEmpresa(Integer empresa){
        List<dtoJuego> catalogo = new ArrayList<>();

        String sqlCatalogo = """
                SELECT j.id_juego, j.nombre_juego, cl.nombre_clasificacion, 
                       ct.nombre_categoria, j.especificaciones, j.descripcion, 
                       j.en_venta, e.nombre_empresa, j.precio,
                       img.url as url_imagen 
                FROM juego j 
                INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
                INNER JOIN categoria ct ON j.id_categoria = ct.id_categoria
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                LEFT JOIN imagen img ON j.url_imagen = img.url 
                WHERE e.id_empresa = ?;
                """;

        try (Connection c = Conexion.obtenerConexion()){ 

            PreparedStatement st = c.prepareStatement(sqlCatalogo);
            st.setInt(1, empresa);
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                dtoJuego juego = new dtoJuego();
                juego.setId(resultado.getInt("id_juego"));
                juego.setNombreJuego(resultado.getString("nombre_juego"));
                juego.setPrecio(resultado.getInt("precio"));
                juego.setDescripcion(resultado.getString("descripcion"));
                juego.setEspecificaciones(resultado.getString("especificaciones"));
                juego.setEnVenta(resultado.getBoolean("en_venta"));

                String urlImagen = resultado.getString("url_imagen");
                if (urlImagen != null) {
                    juego.setUrlImagen(urlImagen);
                }


                catalogo.add(juego);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogo;
    }
    
   public List<String[]> obtenerVentasEmpresa(Integer idEmpresa) {
    List<String[]> ventas = new ArrayList<>();

    String sqlVentas = """
        SELECT v.dinero_comision, v.dinero_empresa, j.nombre_juego, c.porcentaje 
        FROM venta v
        INNER JOIN juego j ON v.id_juego = j.id_juego
        INNER JOIN empresa e ON j.id_empresa = e.id_empresa
        INNER JOIN comision_empresa ce ON e.id_empresa = ce.id_empresa
        INNER JOIN comision c ON ce.id_comision = c.id_comision
        WHERE e.id_empresa = ?;
    """;

    try (Connection c = Conexion.obtenerConexion();
         PreparedStatement st = c.prepareStatement(sqlVentas)) {

        st.setInt(1, idEmpresa);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            String juego = rs.getString("nombre_juego");
            double precioBase = rs.getDouble("dinero_comision") + rs.getDouble("dinero_empresa");
            int porcentaje = rs.getInt("porcentaje");
            double comisionJuego = (precioBase * porcentaje)/100;

            double gananciaNetaVenta = precioBase; 

            boolean encontrado = false;

            for (String[] fila : ventas) {
                if (fila[0].equals(juego)) {
                    double nuevaGananciaNetaTotal = Double.parseDouble(fila[4]) + gananciaNetaVenta;
                    fila[4] = String.valueOf(nuevaGananciaNetaTotal);

                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                String[] nuevaFila = new String[5];
                nuevaFila[0] = juego;
                nuevaFila[1] = String.valueOf(precioBase); 
                nuevaFila[2] = porcentaje + "%";       
                nuevaFila[3] = String.valueOf(comisionJuego);   
                nuevaFila[4] = String.valueOf(gananciaNetaVenta);

                ventas.add(nuevaFila);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return ventas;
}


    public List<String[]> obtenerJuegosMejorCalificadosEmpresa(Integer idEmpresa) {
        List<String[]> juegos = new ArrayList<>();
        String sqlMejores = """
                SELECT j.id_juego, j.nombre_juego, AVG(c.calificacion) as promedio, j.precio, img.url
                FROM juego j
                INNER JOIN calificacion c ON j.id_juego = c.id_juego
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                LEFT JOIN imagen img ON j.url_imagen = img.url
                WHERE e.id_empresa = ?
                GROUP BY j.id_juego, j.nombre_juego, j.precio, img.url
                ORDER BY promedio DESC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlMejores);
            st.setInt(1, idEmpresa);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[5];
                fila[0] = String.valueOf(resultado.getInt("id_juego"));
                fila[1] = resultado.getString("nombre_juego");
                fila[2] = String.format("%.2f", resultado.getDouble("promedio")); 
                fila[3] = String.valueOf(resultado.getInt("precio"));
                fila[4] = resultado.getString("url") != null ? resultado.getString("url") : "";

                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }

    public List<String[]> obtenerJuegosPeorCalificadosEmpresa(Integer idEmpresa) {
        List<String[]> juegos = new ArrayList<>();
        String sqlPeores = """
                SELECT j.id_juego, j.nombre_juego, AVG(c.calificacion) AS promedio, j.precio, img.url
                FROM juego j
                INNER JOIN calificacion c ON j.id_juego = c.id_juego
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                LEFT JOIN imagen img ON j.url_imagen = img.url
                WHERE e.id_empresa = ?
                GROUP BY j.id_juego, j.nombre_juego, j.precio, img.url
                ORDER BY promedio ASC;
                """;

        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlPeores);
            st.setInt(1, idEmpresa);
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String[] fila = new String[5];
                fila[0] = String.valueOf(resultado.getInt("id_juego"));
                fila[1] = resultado.getString("nombre_juego");
                fila[2] = String.format("%.2f", resultado.getDouble("promedio"));
                fila[3] = String.valueOf(resultado.getInt("precio"));
                fila[4] = resultado.getString("url") != null ? resultado.getString("url") : "";

                juegos.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
    
    public List<String[]> obtenerComentariosMejorCalificadosEmpresa(Integer idEmpresa) {
        List<String[]> comentarios = new ArrayList<>();

        String sqlMejoresComentarios = """
                SELECT 
                    com.id_comentario, 
                    com.descripcion, 
                    j.nombre_juego, 
                    com.mail, 
                    AVG(cal.calificacion) as promedio
                FROM comentario com
                INNER JOIN juego j ON com.id_juego = j.id_juego
                INNER JOIN calificacion cal ON com.id_comentario = cal.id_comentario
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                WHERE e.id_empresa = ? 
                  AND com.comentario_padre IS NULL 
                  AND com.es_visible = 1
                GROUP BY com.id_comentario, com.descripcion, j.nombre_juego, com.mail
                ORDER BY promedio DESC;
                """;

        try (Connection conn = Conexion.obtenerConexion()) {
            PreparedStatement st = conn.prepareStatement(sqlMejoresComentarios);
            st.setInt(1, idEmpresa);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String[] fila = new String[5];
                fila[0] = String.valueOf(rs.getInt("id_comentario"));
                String desc = rs.getString("descripcion");
                fila[1] = desc.length() > 50 ? desc.substring(0, 47) + "..." : desc;
                fila[2] = rs.getString("nombre_juego");
                fila[3] = rs.getString("mail");
                fila[4] = String.format("%.2f", rs.getDouble("promedio"));

                comentarios.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comentarios;
    }
    
    public void suspenderVentaJuego(dtoJuego juego){
        String sql="""
            UPDATE juego SET en_venta = FALSE
                WHERE id_juego = ? ;
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, juego.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoJuego buscarJuegoEnCatalogo(String nombreJuego, dtoEmpresa empresa){
        dtoJuego juego = null;
        String sql = """
            SELECT j.id_juego, j.nombre_juego, j.precio, j.en_venta FROM juego j
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            WHERE e.id_empresa = ? AND j.nombre_juego = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, empresa.getIdEmpresa());
            st.setString(2, nombreJuego);
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
}