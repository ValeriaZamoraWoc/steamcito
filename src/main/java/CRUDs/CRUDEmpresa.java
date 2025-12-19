/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Empresas.dtoComision;
import dtos.Empresas.dtoEmpresa;
import dtos.Usuarios.dtoUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import services.General.Conexion;
import models.Empresas.Empresa;
import models.Juegos.Juego;

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

    public List<Juego> obtenerCatalogoEmpresa(Empresa empresa){
        String sqlCatalogo = """
                SELECT j.id_juego, j.nombre_juego, cl.nombre_clasificacion, 
                       ct.nombre_categoria, j.especificaciones, j.descripcion, 
                       j.en_venta, e.nombre_empresa, j.precio 
                FROM juego j 
                INNER JOIN clasificacion cl ON j.id_clasificacion = cl.id_clasificacion
                INNER JOIN categoria ct ON j.id_categoria = ct.id_categoria
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                WHERE e.nombre_empresa = ?;
                """;
        
        try (Connection c = Conexion.obtenerConexion()){ 
            
            PreparedStatement st = c.prepareStatement(sqlCatalogo);
            st.setString(1, empresa.getNombreEmpresa());
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                Juego juego = new Juego(
                    resultado.getInt("id_juego"),
                    resultado.getString("nombre_juego"),
                    resultado.getInt("precio")
                );
                empresa.agregarJuegoCatalogo(juego);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresa.getCatalogo();
    }

    public List<String[]> obtenerVentasEmpresa(Empresa empresa){
        List<String[]> ventas = new ArrayList<>();
        
        String sqlVentas = """
                SELECT v.monto, j.nombre_juego, c.porcentaje 
                FROM venta v
                INNER JOIN juego j ON v.id_juego = j.id_juego
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                INNER JOIN comision_empresa ce ON e.id_empresa = ce.id_empresa
                INNER JOIN comision c ON ce.id_comision = c.id_comision
                WHERE e.nombre_empresa = ?;
                """;

        try (Connection c = Conexion.obtenerConexion()){ 
            PreparedStatement st = c.prepareStatement(sqlVentas);
            st.setString(1, empresa.getNombreEmpresa());
            ResultSet resultado = st.executeQuery();

            while (resultado.next()) {
                String juego = resultado.getString("nombre_juego");
                int monto = resultado.getInt("monto");
                int porcentaje = resultado.getInt("porcentaje");

                boolean encontrado = false;

                for (String[] fila : ventas) {
                    if (fila[0].equals(juego)) {
                        int sumaActual = Integer.parseInt(fila[1]);
                        fila[1] = String.valueOf(sumaActual + monto);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    String[] nuevaFila = new String[5];
                    nuevaFila[0] = juego;         
                    nuevaFila[1] = String.valueOf(monto);
                    nuevaFila[2] = String.valueOf(porcentaje);
                    ventas.add(nuevaFila);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (String[] fila : ventas) {
            int monto = Integer.parseInt(fila[1]);
            int porcentaje = Integer.parseInt(fila[2]);

            int comision = (monto * porcentaje) / 100;
            int gananciaNeta = monto - comision;

            fila[3] = String.valueOf(comision);
            fila[4] = String.valueOf(gananciaNeta);
        }

        return ventas;
    }

    public List<Juego> obtenerJuegosMejorCalificadosEmpresa(Empresa empresa){
        List<Juego> juegos = new ArrayList<>();
        String sqlMejores = """
                SELECT j.id_juego, j.nombre_juego, c.calificacion, j.precio 
                FROM calificacion c
                INNER JOIN juego j ON c.id_juego = j.id_juego
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                WHERE e.nombre_empresa = ? 
                ORDER BY c.calificacion DESC;
                """;
        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlMejores);
            st.setString(1, empresa.getNombreEmpresa());
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                Juego juego = new Juego(
                    resultado.getInt("id_juego"),
                    resultado.getString("nombre_juego"),
                    resultado.getInt("precio")
                );
                juegos.add(juego);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }

    public List<Juego> obtenerJuegosPeorCalificadiosEmpresa(Empresa empresa){
        List<Juego> juegos = new ArrayList<>();
        String sqlPeores = """
                SELECT j.id_juego, j.nombre_juego, c.calificacion, j.precio 
                FROM calificacion c
                INNER JOIN juego j ON c.id_juego = j.id_juego
                INNER JOIN empresa e ON j.id_empresa = e.id_empresa
                WHERE e.nombre_empresa = ? 
                ORDER BY c.calificacion ASC;
                """;
        try (Connection c = Conexion.obtenerConexion()) {
            PreparedStatement st = c.prepareStatement(sqlPeores);
            st.setString(1, empresa.getNombreEmpresa());
            ResultSet resultado = st.executeQuery();

            while(resultado.next()){
                Juego juego = new Juego(
                    resultado.getInt("id_juego"),
                    resultado.getString("nombre_juego"),
                    resultado.getInt("precio")
                );
                juegos.add(juego);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
}