/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Juegos.dtoJuego;
import dtos.ObjetosUsuario.dtoBibliotecaJuego;
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
public class CRUDVenta {
    
    public void registrarVenta(dtoBibliotecaJuego bj, int monto){
        double[] dinero = calcularComision(bj.getIdJuego());
        String sql = """
            INSERT INTO venta (mail, id_juego, dinero_comision, dinero_empresa, fecha)
            VALUES (?, ?, ?, ?, CURDATE());
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, bj.getMailBiblioteca());
            st.setInt(2, bj.getIdJuego());
            st.setDouble(3, dinero[0]);
            st.setDouble(4, dinero[1]);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private double[] calcularComision(Integer idJuego){
        double[] dinero = new double[2];
        String sql ="""
            SELECT c.porcentaje, j.precio FROM juego j
            INNER JOIN empresa e ON j.id_empresa = e.id_empresa
            INNER JOIN comision_empresa ce ON e.id_empresa = ce.id_empresa
            INNER JOIN comision c ON ce.id_comision = c.id_comision
            WHERE j.id_juego = ? ;
                    """;
        
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idJuego);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
                double precio = rs.getInt("precio");
                double porcentaje = rs.getInt("porcentaje");
                
                dinero[0] = precio *(porcentaje/100);//comision
                dinero[1] = precio -dinero[0];//ganancia
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dinero;
    }

    public List<String[]> obtenerVentasUsuario(String mail){
        List<String[]> ventas = new ArrayList<>();
        String sql = """
            SELECT j.nombre_juego, v.monto, v.fecha
            FROM venta v
            INNER JOIN juego j ON v.id_juego = j.id_juego
            WHERE v.mail = ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                String[] fila = new String[3];
                fila[0] = rs.getString("nombre_juego");
                fila[1] = String.valueOf(rs.getInt("monto"));
                fila[2] = rs.getDate("fecha").toString();
                ventas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

    public int totalVentasJuego(int idJuego){
        int total = 0;
        String sql = """
            SELECT SUM(monto) AS total
            FROM venta
            WHERE id_juego = ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idJuego);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
