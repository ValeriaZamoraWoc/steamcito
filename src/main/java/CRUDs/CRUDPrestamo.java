/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.ObjetosUsuario.dtoPrestamo;
import java.sql.Connection;
import java.sql.Date;
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
public class CRUDPrestamo {
     public void registrarPrestamo(dtoPrestamo prestamo){
        String sql = """
            INSERT INTO prestamo
            (mail_prestamista, mail_dueno, id_juego,
             fecha_prestamo, devuelto)
            VALUES (?, ?, ?, ?, ?);
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, prestamo.getMailPrestamista());
            st.setString(2, prestamo.getMailDueno());
            st.setInt(3, prestamo.getIdJuego());
            st.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            st.setBoolean(5, prestamo.isDevuelto());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void devolverPrestamo(dtoPrestamo prestamo){
        String sql = """
            UPDATE prestamo
            SET devuelto = ?, fecha_devolucion = ?
            WHERE id_prestamo = ?;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setBoolean(1, prestamo.isDevuelto());
            st.setDate(2, Date.valueOf(prestamo.getFechaDevolucion()));
            st.setInt(3, prestamo.getIdPrestamo());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<dtoPrestamo> prestamosActivosUsuario(String mail){
        List<dtoPrestamo> prestamos = new ArrayList<>();

        String sql = """
            SELECT id_prestamo, id_juego, fecha_prestamo
            FROM prestamo
            WHERE mail_prestamista = ? AND devuelto = FALSE;
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setString(1, mail);
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                dtoPrestamo p = new dtoPrestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdJuego(rs.getInt("id_juego"));
                p.setFechaPrestamo(rs.getDate("fecha_prestamo").toLocalDate());
                p.setDevuelto(false);
                prestamos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prestamos;
    }

}
