package services.General;

import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author cacerola
 */
public class ConectorBD {
    /*private DAOempresa requestEmpresa;
    private DAOjuego requestJuego;
    private DAOusuario requestUsuario;
    private DAOsistema requestSistema;
    private TipoBusqueda tipo;

    public ConectorBD(){
       /* requestEmpresa = new DAOempresa();
        requestJuego= new DAOjuego();
        requestUsuario= new DAOusuario();
        limpiar();
    }

    private void limpiar(){
        tipo=null;
    }

    //registrar datos
    //usuarios
    public void registrarUsuario(Usuario usuario){
        requestUsuario.registrarUsuario(usuario);
    }
    
    public Usuario iniciarSesionSistema(String mail, String contraseña){
        return requestSistema.inicioSesion(mail, contraseña);
    }
    
    //buscador general
    //juegos
    public Juego buscarJuegoTitulo(String texto){
        return requestJuego.buscarJuegoPorTitulo(texto);
    }
    public List<Juego> buscarJuegoCategoria(String texto){
        return requestJuego.buscarJuegosPorCategoria(texto);
    }
    public List<Juego> buscarJuegoEmpresa(String texto){
        return requestJuego.buscarJuegosPorEmpresa(texto);
    }
    //empresas
    public Empresa buscarEmpresa(String texto){
        return requestEmpresa.buscarEmpresa(texto);
    }
    public List<Juego> buscarCatalogoEmpresas(String texto){
        Empresa empresa = buscarEmpresa(texto);
        return requestEmpresa.obtenerCatalogo(empresa);
    }
    //usuarios comunes
    public UsuarioComun buscarUsuarioComun(String texto){
        return requestUsuario.buscarUsuario(texto);
    }
    public List<Juego> buscarBibliotecaUsuarioComun(String texto){
        UsuarioComun usuario = buscarUsuarioComun(texto);
        return requestUsuario.obtenerBibliotecaUsuario(usuario);
    }

    //búsqueda para reportes
    public List<String[]> buscarHistorialComprasUsuarioComun(String texto){
        UsuarioComun usuario = buscarUsuarioComun(texto);
        return requestUsuario.historialGastosUsuario(usuario);
    }*/
}
