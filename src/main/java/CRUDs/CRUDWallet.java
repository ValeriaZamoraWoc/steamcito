/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.ObjetosUsuario.dtoWallet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            st.executeQuery();

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
}
