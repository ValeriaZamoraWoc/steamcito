/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDCategoria;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoUsuario;
import dtos.Usuarios.dtoUsuarioComun;
import dtos.Usuarios.dtoUsuarioDesarrollador;

/**
 *
 * @author cacerola
 */
public class EliminarCategoria {
    private CRUDCategoria cC= new CRUDCategoria();
    private CRUDUsuario cU= new CRUDUsuario();
    private CRUDJuego cJ= new CRUDJuego();
    
    public String eliminarCategoriaPorAdmin(Integer idCategoria, String mail){
        dtoUsuario usuario = cU.buscarUsuarioComunPorMail(mail);
        
        if (usuario == null) {
            return "Usuario no existe";
        }
        
        if(!(usuario instanceof dtoUsuarioComun) && !(usuario instanceof dtoUsuarioDesarrollador)){
            dtoCategoria categoria = cC.buscarCategoriaPorId(idCategoria);
            
            if(categoria!= null){
                cC.eliminarCategoria(categoria.getIdCategoria());
                
            }else{
                if (categoria == null) {
                    return "Categoría no existe";
                }
            }
            
        }else{
            return "Usuario no tiene permisos para eliminar categoría";
        }
        return "Categoría eliminada correctamente"; 
    }
    
    public String cambiarCategoriaDeJuegoPorDesarrollador(Integer idCategoria, Integer idJuego, String mail){
        dtoUsuario usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorId(idJuego);
        if (usuario == null) {
            return "Usuario no existe";
        }
        
        if (juego == null) {
            return "Juego no existe";
        }
        
        if(usuario instanceof dtoUsuarioDesarrollador){
            dtoCategoria categoria = cC.buscarCategoriaPorId(idCategoria);
            cJ.cambiarCategoriaJuego(juego, categoria);

            
        }else{
            return "Usuario no tiene permisos para cambiar categoría";
        }
        return "Categoría cambiada correctamente"; 
    }
    
    
    public String cambiarCategoriaDeJuegoPorAdmin(Integer idCategoria, Integer idJuego, String mail){
        dtoUsuario usuario = cU.buscarUsuarioComunPorMail(mail);
        dtoJuego juego = cJ.buscarJuegoPorId(idJuego);
        if (usuario == null) {
            return "Usuario no existe";
        }
        
        if (juego == null) {
            return "Juego no existe";
        }
        
        if(!(usuario instanceof dtoUsuarioDesarrollador) || !(usuario instanceof dtoUsuarioComun)){
            dtoCategoria categoria = cC.buscarCategoriaPorId(idCategoria);
            cJ.cambiarCategoriaJuego(juego, categoria);

            
        }else{
            return "Usuario no tiene permisos para cambiar categoría";
        }
        return "Categoría cambiada correctamente"; 
    }
}
