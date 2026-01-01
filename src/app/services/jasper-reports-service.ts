import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

@Injectable({
  providedIn: 'root'
})
export class JasperReportsService {

  constructor(private http: HttpClient) {}

  //los de administrador
    gananciasPorEmpresaAdmin(): Observable<Blob> {
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/ganancias-por-empresa`,
            { responseType: 'blob' }
        );
    }

    masVendidosCategoriaAdmin(categoria: string): Observable<Blob> {
        const params = new HttpParams().set('categoria', categoria.toString());
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/juegos-mas-vendidos/categoria`,
            { responseType: 'blob' }
        );
    }

    masVendidosClasificacionAdmin(clasificacion: string): Observable<Blob> {
        const params = new HttpParams().set('clasificacion', clasificacion.toString());
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/juegos-mas-vendidos/clasificacion`,
            { params, responseType: 'blob' }
        );
    }

    mejorCalificadosClasificacionAdmin(clasificacion: string): Observable<Blob> {
         const params = new HttpParams().set('clasificacion', clasificacion.toString());
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/mejores-calificados/clasificacion`,
            { params, responseType: 'blob' }
        );
    }

    gananciasGlobalesAdmin(): Observable<Blob> {
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/ganancias-globales`,
            { responseType: 'blob' }
        );
    }

    mejorCalificadosCategoriaAdmin(categoria: string): Observable<Blob> {
        const params = new HttpParams().set('categoria', categoria.toString());
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/mejores-calificados/categoria`,
            { params, responseType: 'blob' }
        );
    }

    jugadoresConMasJuegosAdmin(): Observable<Blob> {
        return this.http.get(
            `${API_BASE_URL}/admin/reporte/jugadores-con-mas-juegos`,
            { responseType: 'blob' }
        );
    }


    //los de empresa
    comentariosMejorCalificadosEmpresa(idEmpresa: number): Observable<Blob> {
        const params = new HttpParams().set('idEmpresa', idEmpresa.toString());
        return this.http.get(
            `${API_BASE_URL}/empresa/reporte/comentarios-mejor-calificados`,
            { params, responseType: 'blob' }
        );
    }

    juegosMejorCalificadosEmpresa(idEmpresa: number): Observable<Blob> {
        const params = new HttpParams().set('idEmpresa', idEmpresa.toString());
        return this.http.get(
            `${API_BASE_URL}/empresa/reporte/juegos-mejor-calificados`,
            { params, responseType: 'blob' }
        );
    }

    juegosPeorCalificadosEmpresa(idEmpresa: number): Observable<Blob> {
        const params = new HttpParams().set('idEmpresa', idEmpresa.toString());
        return this.http.get(
            `${API_BASE_URL}/empresa/reporte/juegos-peor-calificados`,
            { params, responseType: 'blob' }
        );
    }

    reporteVentasEmpresa(idEmpresa: number): Observable<Blob> {
        const params = new HttpParams().set('idEmpresa', idEmpresa.toString());
        return this.http.get(
            `${API_BASE_URL}/empresa/reporte/ventas`,
            { params, responseType: 'blob' }
        );
    }

    top5JuegosEmpresa(idEmpresa: number): Observable<Blob> {
        const params = new HttpParams().set('idEmpresa', idEmpresa.toString());
        return this.http.get(
            `${API_BASE_URL}/empresa/reporte/top5-juegos`,
            { params, responseType: 'blob' }
        );
    }

    //usuario comun
    calificacionesPersonales(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/calificaciones-personales`,
            { params, responseType: 'blob' }
        );
    }

    clasificacionesPersonalesFavoritas(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/clasificaciones-favoritas`,
            { params, responseType: 'blob' }
        );
    }

    historialDeGastos(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/historial-gastos`,
            { params, responseType: 'blob' }
        );
    }

    juegosMejorCalificadosUsuario(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/juegos-mejor-calificados`,
            { params, responseType: 'blob' }
        );
    }

    prestadosMasJugados(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/prestados-mas-jugados`,
            { params, responseType: 'blob' }
        );
    }

    prestadosMejorCalificados(mail: string): Observable<Blob> {
        const params = new HttpParams().set('mail', mail.toString());
        return this.http.get(
            `${API_BASE_URL}/usuario/reporte/prestados-mejor-calificados`,
            { params, responseType: 'blob' }
        );
    }

}
