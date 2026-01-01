/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.ReportesAdminJasperReports;

import CRUDs.CRUDBiblioteca;
import CRUDs.CRUDGrupoFamiliar;
import CRUDs.CRUDWallet;
import dtos.ReportesJasperReports.Usuario.CalificacionPersonalDTO;
import dtos.ReportesJasperReports.Usuario.ClasificacionFavoritaDTO;
import dtos.ReportesJasperReports.Usuario.HistorialGastoDTO;
import dtos.ReportesJasperReports.Usuario.JuegoMejorCalificadoDTO;
import dtos.ReportesJasperReports.Usuario.PrestadoMasJugadoDTO;
import dtos.ReportesJasperReports.Usuario.PrestadoMejorCalificadoDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cacerola
 */


public class ReportesUsuarioJasperReports {
    
    private CRUDBiblioteca b = new CRUDBiblioteca();
    private CRUDWallet w = new CRUDWallet();
    private CRUDGrupoFamiliar cf= new CRUDGrupoFamiliar();

    public List<HistorialGastoDTO> obtenerHistorialGastos(String mail) {
        List<String> registros = w.historialDeWallet(mail);
        List<HistorialGastoDTO> lista = new ArrayList<>();

        int contador = 1;
        for (String r : registros) {
            int ultimoEspacio = r.lastIndexOf(' ');
            int penultimoEspacio = r.lastIndexOf(' ', ultimoEspacio - 1);

            int precio = Integer.parseInt(r.substring(ultimoEspacio + 1).replace("-", ""));
            LocalDate fecha = LocalDate.parse(r.substring(penultimoEspacio + 1, ultimoEspacio));

            int puntoIndex = r.indexOf('.') + 2;
            String nombreJuego = r.substring(puntoIndex, penultimoEspacio);

            lista.add(new HistorialGastoDTO(
                    contador++,
                    nombreJuego,
                    fecha,
                    precio
            ));
        }
        return lista;
    }


    public List<JuegoMejorCalificadoDTO> obtenerMejoresCalificadosBiblioteca(String mail) {
        List<String[]> datos = b.obtenerMejoresCalificadosBiblioteca(mail);
        List<JuegoMejorCalificadoDTO> lista = new ArrayList<>();

        for (String[] fila : datos) {
            lista.add(new JuegoMejorCalificadoDTO(
                    Integer.parseInt(fila[0]),
                    fila[1],
                    Double.parseDouble(fila[2]),
                    fila[3]
            ));
        }
        return lista;
    }

    public List<CalificacionPersonalDTO> obtenerCalificacionesPersonales(String mail) {
        List<String[]> datos = b.obtenerCalificacionesPersonales(mail);
        List<CalificacionPersonalDTO> lista = new ArrayList<>();

        for (String[] fila : datos) {
            lista.add(new CalificacionPersonalDTO(
                    Integer.parseInt(fila[0]),
                    fila[1],
                    Integer.parseInt(fila[2]),
                    fila[3]
            ));
        }
        return lista;
    }

    public List<ClasificacionFavoritaDTO> obtenerClasificacionesFavoritas(String mail) {
        List<String[]> datos = b.obtenerClasificacionesFavoritas(mail);
        List<ClasificacionFavoritaDTO> lista = new ArrayList<>();

        for (String[] fila : datos) {
            lista.add(new ClasificacionFavoritaDTO(
                    fila[0],
                    Integer.parseInt(fila[1])
            ));
        }
        return lista;
    }
    
    public List<PrestadoMasJugadoDTO> obtenerPrestadosMasJugados(String mail) {
        List<String[]> datos = cf.obtenerPrestadosMasJugados(mail);
        List<PrestadoMasJugadoDTO> lista = new ArrayList<>();

        for (String[] fila : datos) {
            lista.add(new PrestadoMasJugadoDTO(
                    fila[0],
                    Integer.parseInt(fila[1]),
                    fila[2]
            ));
        }
        return lista;
    }
    
    public List<PrestadoMejorCalificadoDTO> obtenerMejoresCalificadosPrestados(String mail) {
        List<String[]> datos = cf.obtenerMejoresCalificadosPrestados(mail);
        List<PrestadoMejorCalificadoDTO> lista = new ArrayList<>();

        for (String[] fila : datos) {
            lista.add(new PrestadoMejorCalificadoDTO(
                    Integer.parseInt(fila[0]),
                    fila[1],
                    Double.parseDouble(fila[2]),
                    fila[3]
            ));
        }
        return lista;
    }
}
