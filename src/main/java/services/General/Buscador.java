/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.General;

import CRUDs.CRUDCategoria;
import CRUDs.CRUDClasificacion;
import CRUDs.CRUDEmpresa;
import CRUDs.CRUDGrupoFamiliar;
import CRUDs.CRUDJuego;
import CRUDs.CRUDUsuario;
import dtos.Empresas.dtoEmpresa;
import dtos.Juegos.dtoJuego;
import dtos.Usuarios.dtoGrupoFamiliar;
import dtos.Usuarios.dtoUsuarioComun;
import java.util.List;

/**
 *
 * @author cacerola
 */
public class Buscador {
    //se puede buscar por usuario, empresa, juego, categoria, clasificacion y esos serán los tipos
    CRUDUsuario cu = new CRUDUsuario();
    CRUDEmpresa ce= new CRUDEmpresa();
    CRUDJuego cj= new CRUDJuego();
    CRUDClasificacion cl = new CRUDClasificacion();
    CRUDCategoria ct= new CRUDCategoria();
    CRUDGrupoFamiliar gp = new CRUDGrupoFamiliar();
    //los usuarios y empresas solo se buscaran por nick o mail. Los juegos
    //se podrán buscar por nombre pero tambien por categoria y clasificacion
    //esos nos daran una lista
    
    //usuario
    public dtoUsuarioComun buscarUsuarioComunPorMail(String mail){
        return cu.buscarUsuarioComunPorMail(mail);
    }
    public dtoUsuarioComun buscarUsuarioComunPorNick(String nick){
        return cu.buscarUsuarioComunPorNickname(nick);
    }
    
    //empresa
    public dtoEmpresa buscarEmpresaPorNombre(String nombre){
        return ce.buscarEmpresaPorNombre(nombre);
    }
    
    //clasificacion
    public List<dtoJuego> buscarJuegosPorClasificacion(String clasificacion) {
        return cl.buscarJuegosPorClasificacion(clasificacion);
    }
    
    //categoria 
    public List<dtoJuego> buscarJuegosPorCategoria(String categoria){
        return ct.buscarJuegosPorCategoria(categoria);
    }
    
    //juego
    public dtoJuego buscarJuegoPorNombre(String nombreJuego){
        return cj.buscarJuegoPorTitulo(nombreJuego);
    }
    
    //grupo familiar
    public dtoGrupoFamiliar buscarGrupoFamiliarPorNombre(String nombreGrupo){
        return gp.obtenerGrupoFamiliar(nombreGrupo);
    }
}
