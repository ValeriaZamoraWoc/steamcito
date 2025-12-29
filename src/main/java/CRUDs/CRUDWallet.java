/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.ObjetosUsuario.dtoWallet;
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
public class CRUDWallet {
    
    public void registrarWallet(dtoWallet dto){
        String sql = """
            INSERT INTO wallet (mail, saldo) VALUES (?, 0);
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, dto.getMail());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoWallet obtenerWallet(String mail){
        dtoWallet wallet = null;

        String sql = """
            SELECT mail, saldo
            FROM wallet
            WHERE mail = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            if(rs.next()){
                wallet = new dtoWallet();
                wallet.setMail(rs.getString("mail"));
                wallet.setSaldo(rs.getInt("saldo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    public void recargarSaldo(dtoWallet wallet, Integer monto){
        String sql = """
        UPDATE wallet SET saldo =  saldo + ? 
        WHERE mail = ?;
        """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, monto);
            st.setString(2, wallet.getMail());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean descontarSaldo(dtoWallet wallet, Integer monto){
        String sql = """
            UPDATE wallet SET saldo = saldo - ?
            WHERE mail = ? AND saldo >= ? ;
            """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, monto);
            st.setString(2, wallet.getMail());
            st.setInt(3, monto);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<String> historialDeWallet(String mail){
        List<String> historial = new ArrayList<>();
        int contador =1;
        String sql = """
            SELECT j.nombre_juego, v.fecha, j.precio FROM venta v
            INNER JOIN wallet w ON v.mail = w.mail
            INNER JOIN juego j ON j.id_juego = v.id_juego
            WHERE v.mail = ? ;
                     """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()){
                Date fecha1= rs.getDate("fecha");
                LocalDate fecha2 = fecha1.toLocalDate();
                String registro = contador +". "+ rs.getString("nombre_juego")+" "
                        + fecha2.toString()+" -"+rs.getInt("precio");
                historial.add(registro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}
