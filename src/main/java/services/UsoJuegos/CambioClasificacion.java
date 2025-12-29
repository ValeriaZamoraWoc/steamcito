/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDCategoria;
import CRUDs.CRUDClasificacion;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoClasificacion;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioComun;
import dtos.Usuarios.dtoUsuarioDesarrollador;

/**
 *
 * @author cacerola
 */
public class CambioClasificacion {
    private CRUDClasificacion cC= new CRUDClasificacion();
    private CRUDUsuario cU= new CRUDUsuario();
    private CRUDJuego cJ= new CRUDJuego();
    
    public String eliminarClasificacionPorAdmin(Integer idClasificacion) {
        dtoClasificacion clasificacion = cC.buscarClasificacionPorId(idClasificacion);

        if (clasificacion == null) {
            return "Clasificación no existe";
        }

        cC.eliminarClasificacion(clasificacion);
        return "Clasificación eliminada correctamente";
    }
    
    
    public String cambiarClasificacionDeJuegoPorAdmin(Integer idClasificacion, Integer idJuego){
        dtoJuego juego = cJ.buscarJuegoPorId(idJuego);
        
        if (juego == null) {
            return "Juego no existe";
        }
        
            dtoClasificacion categoria = cC.buscarClasificacionPorId(idClasificacion);
            if (categoria == null) {
                return "Clasificación no existe";
            }
            cJ.cambiarClasificacionJuego(juego, categoria);

        return "Categoría cambiada correctamente"; 
    }
}
