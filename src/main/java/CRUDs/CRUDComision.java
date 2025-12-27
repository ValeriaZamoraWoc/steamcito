/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Empresas.dtoComision;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDComision {
    public void registrarComision(dtoComision comision){
        String sql="""
            INSERT INTO comision (porcentaje) VALUES (?);
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, comision.getPorcentajeComision());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void registrarComisionConPorcentaje(Integer comision){
        String sql="""
            INSERT INTO comision (porcentaje) VALUES (?);
                   """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, comision);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarComision(int idComision){
        String sql ="""
            DELETE FROM comision WHERE id_comision = ? ;
                    """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idComision);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public dtoComision buscarComision(Integer porcentaje){
        dtoComision comision = null;
        String sql ="""
            SELECT * FROM comision WHERE porcentaje = ? ;
                    """;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, porcentaje);
            ResultSet rs=st.executeQuery();
            
            if(rs.next()){
                comision = new dtoComision();
                comision.setIdComision(rs.getInt("id_comision"));
                comision.setPorcentaje(rs.getInt("porcentaje"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comision;
    }
    
    public void modificarComisionEmpresa(Integer idEmpresa, Integer idComision){
        String sql ="""
            UPDATE comision_empresa SET id_comision = ? WHERE id_empresa = ?;
                    """ ;
        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idComision);
            st.setInt(2, idEmpresa);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
