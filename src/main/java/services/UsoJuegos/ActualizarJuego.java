/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.UsoJuegos;

import CRUDs.CRUDCategoria;
import CRUDs.CRUDClasificacion;
import CRUDs.CRUDEmpresa;
import CRUDs.CRUDJuego;
import dtos.Empresas.dtoEmpresa;
import dtos.Juegos.dtoCategoria;
import dtos.Juegos.dtoClasificacion;
import dtos.Juegos.dtoJuego;
import java.time.LocalDate;

/**
 *
 * @author cacerola
 */
public class ActualizarJuego {
    CRUDJuego crudJuego = new CRUDJuego();
    CRUDClasificacion crudClasificacion = new CRUDClasificacion();
    CRUDCategoria crudCategoria = new CRUDCategoria();
    CRUDEmpresa cE= new CRUDEmpresa();
    
    public void actualizarJuego(String nombreJuego, String descripcion, String especificaciones,
        String clasificacion, String categoria, int precio, LocalDate fechaLanzamiento ){
        dtoJuego juego = new dtoJuego();
        juego.setNombreJuego(nombreJuego);
        juego.setDescripcion(descripcion);
        juego.setEspecificaciones(especificaciones);
        juego.setClasificacion(encontrarClasificacion(clasificacion));
        juego.setCategoria(encontrarCategoria(categoria));
        juego.setPrecio(precio);
        juego.setFechaLanzamiento(fechaLanzamiento);
        
        crudJuego.actualizarJuego(juego);

    }
    
    private int encontrarClasificacion(String nombreClasificacion){
        dtoClasificacion clasificacion = crudClasificacion.buscarClasificacionPorNombre(nombreClasificacion);
        if (clasificacion == null) throw new IllegalArgumentException("Clasificación inválida");
        return clasificacion.getIdClasificacion();
    }
    
    private int encontrarCategoria(String nombreCategoria){
        dtoCategoria categoria = crudCategoria.buscarCategoriaPorNombre(nombreCategoria);
        if (categoria == null) throw new IllegalArgumentException("Categoria inválida");
        return categoria.getIdCategoria();
    }
    
    public void suspenderVentaJuego(String nombreJuego, String nombreEmpresa){
        dtoEmpresa empresa = cE.buscarEmpresaPorNombre(nombreEmpresa);
        dtoJuego juego = cE.buscarJuegoEnCatalogo(nombreJuego, empresa);
        
        if(juego != null){
            cE.suspenderVentaJuego(juego);
        }
    }
}
