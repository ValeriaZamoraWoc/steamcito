/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CRUDs;

import dtos.Empresas.dtoEmpresa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import services.General.Conexion;

/**
 *
 * @author cacerola
 */
public class CRUDPeticionComision {
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
}
