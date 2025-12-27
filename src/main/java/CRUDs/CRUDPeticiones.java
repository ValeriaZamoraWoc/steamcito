/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Empresas.EstadoPeticion;
import dtos.Empresas.dtoEmpresa;
import dtos.Empresas.dtoPeticionComision;
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
public class CRUDPeticiones {
    public boolean existePeticion(Integer idEmpresa){
        String sql = """
            SELECT * FROM peticion_comision WHERE id_empresa = ? AND estado = 'Pendiente';
                     """;
        try (Connection c = Conexion.obtenerConexion()) {
           PreparedStatement st = c.prepareStatement(sql);
           st.setInt(1, idEmpresa);
           ResultSet rs = st.executeQuery();

           if (rs.next()) {
               return true;
           } else {
               return false; 
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<dtoPeticionComision> obtenerPeticiones(){
        List<dtoPeticionComision> peticiones = new ArrayList<>();
        String sql = """
            SELECT p.porcentaje, p.estado, e.nombre_empresa, p.id_empresa FROM peticion_comision p
            INNER JOIN empresa e ON p.id_empresa= e.id_empresa
                     WHERE estado = 'Pendiente';
                     """;
        try (Connection c = Conexion.obtenerConexion()) {
           PreparedStatement st = c.prepareStatement(sql);
           ResultSet rs = st.executeQuery();

           if (rs.next()) {
               dtoPeticionComision p= new dtoPeticionComision();
               p.setIdEmpresa(rs.getInt("id_empresa"));
               p.setEmpresa(rs.getString("nombre_empresa"));
               p.setPorcentaje(rs.getInt("porcentaje"));
               String estado = rs.getString("estado");
               if (estado != null) {
                p.setEstado(EstadoPeticion.valueOf(estado));
            }
               peticiones.add(p);
           } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return peticiones;
    }
    
    public void aceptarPeticion(Integer idEmpresa){
        String sql = """
            UPDATE peticion_comision SET estado = 'Aceptada' WHERE id_empresa = ?;
                     """;
        try (Connection c = Conexion.obtenerConexion()) {
           PreparedStatement st = c.prepareStatement(sql);
           st.setInt(1, idEmpresa);
           st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rechazarPeticion(Integer idEmpresa){
        String sql = """
            UPDATE peticion_comision SET estado = 'Rechazada' WHERE id_empresa = ?;
                     """;
        try (Connection c = Conexion.obtenerConexion()) {
           PreparedStatement st = c.prepareStatement(sql);
           st.setInt(1, idEmpresa);
           st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void registrarPeticion(dtoEmpresa empresa, Integer porcentaje){
        String sql = """
            INSERT INTO peticion_comision (id_empresa, porcentaje, estado)
            VALUES (?, ?, 'Pendiente');
            """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, empresa.getIdEmpresa());
            st.setInt(2, porcentaje);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public dtoPeticionComision obtenerPeticion(Integer idEmpresa){
        dtoPeticionComision pC= null;
        String sql = """
            SELECT * FROM peticion_comision WHERE id_empresa = ? AND estado = 'Aceptada';
                     """;

        try (Connection c = Conexion.obtenerConexion()){
            PreparedStatement st = c.prepareStatement(sql);
            st.setInt(1, idEmpresa);
            ResultSet rs = st.executeQuery();
            
            if(rs.next()){
              pC= new dtoPeticionComision();  
              pC.setIdEmpresa(rs.getInt("id_empresa"));
              pC.setPorcentaje(rs.getInt("porcentaje"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pC;
    }
}
