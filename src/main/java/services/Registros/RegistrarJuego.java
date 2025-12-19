/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.Registros;

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
public class RegistrarJuego {
    CRUDJuego crudJuego = new CRUDJuego();
    CRUDClasificacion crudClasificacion = new CRUDClasificacion();
    CRUDCategoria crudCategoria = new CRUDCategoria();
    CRUDEmpresa crudEmpresa = new CRUDEmpresa();
    
    public void registrarJuego(String nombreJuego, String descripcion, String especificaciones,
            String clasificacion, String categoria, String empresa, int precio, LocalDate fechaLanzamiento ){
        dtoJuego juego = new dtoJuego();
        juego.setNombreJuego(nombreJuego);
        juego.setDescripcion(descripcion);
        juego.setEspecificaciones(especificaciones);
        juego.setClasificacion(encontrarClasificacion(clasificacion));
        juego.setCategoria(encontrarCategoria(categoria));
        juego.setEmpresa(encontrarEmpresa(empresa));
        juego.setPrecio(precio);
        juego.setFechaLanzamiento(fechaLanzamiento);
        juego.setEnVenta(true);
        
        crudJuego.registrarJuego(juego);
        
    }
    
    private int encontrarEmpresa(String nombreEmpresa){
        dtoEmpresa empresa =crudEmpresa.buscarEmpresaPorNombre(nombreEmpresa);
        return empresa.getIdEmpresa();
    }
    
    private int encontrarClasificacion(String nombreClasificacion){
        dtoClasificacion clasificacion = crudClasificacion.buscarClasificacionPorNombre(nombreClasificacion);
        return clasificacion.getIdClasificacion();
    }
    
    private int encontrarCategoria(String nombreCategoria){
        dtoCategoria categoria = crudCategoria.buscarCategoriaPorNombre(nombreCategoria);
        return categoria.getIdCategoria();
    }
}
