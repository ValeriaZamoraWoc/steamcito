import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { API_BASE_URL } from "../core/api.config";
import { Injectable } from "@angular/core";
import { switchMap, map } from 'rxjs/operators';
import { CompanyService } from './company-service';

export interface Juego{
    id: number, nombreJuego: string, descripcion: string,  especificaciones:string, clasificacion: number, categoria: number,
  empresa: number, precio: number, enVenta: boolean, fechaLanzamiento: Date, urlImagen: string
}

export interface RespuestaInstalacion {
  instalado: boolean;
  mensaje?: string;
  encontrado?: boolean;
  detalle?: string;
  grupos?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class GameService {
  constructor(private http: HttpClient, private companyService: CompanyService) {}

  obtenerTodos(): Observable<Juego[]> {
    return this.http.get<Juego[]>(
      `${API_BASE_URL}/obtenerJuegos`
    );
  }

  obtenerJuego(id: number): Observable<Juego> {
    return this.http.get<Juego>(
      `${API_BASE_URL}/obtenerJuegoPorId`,
      { params: { idJuego: id } }
    ).pipe(
      switchMap(juego => {
        return this.companyService.obtenerPerfilEmpresa(juego.empresa).pipe(
          map(response => {
            if ('catalogo' in response && response.catalogo) {
              const juegoEmpresa = response.catalogo.find(j => j.id === juego.id);
              if (juegoEmpresa) {
                juego.enVenta = juegoEmpresa.enVenta;
              }
            }
            return juego;
          })
        );
      })
    );
  }

  registrarJuego(data: {
    nombre: string;
    descripcion: string;
    especificaciones: string;
    clasificacion: string;
    categoria: string;
    empresa: string;
    precio: number;
    fechaLanzamiento: string;
  }):  Observable<{ idJuego: number; mensaje: string }> {

    const body = new URLSearchParams();
    body.set('nombre', data.nombre);
    body.set('descripcion', data.descripcion);
    body.set('especificaciones', data.especificaciones);
    body.set('clasificacion', data.clasificacion);
    body.set('categoria', data.categoria);
    body.set('empresa', data.empresa.toString());
    body.set('precio', data.precio.toString());
    body.set('fechaLanzamiento', data.fechaLanzamiento);

    return this.http.post<{ idJuego: number; mensaje: string }>(
      `${API_BASE_URL}/registroJuego`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
    );
  }

  actualizarJuego(data: {
    idJuego: number;
    nombreJuego: string;
    descripcion: string;
    especificaciones: string;
    clasificacion: number;
    categoria: number;
    precio: number;
    fechaLanzamiento: string;
  }): Observable<any> {
    const body = new URLSearchParams();
    body.set('idJuego', data.idJuego.toString());
    body.set('nombreJuego', data.nombreJuego);
    body.set('descripcion', data.descripcion);
    body.set('especificaciones', data.especificaciones);
    body.set('clasificacion', data.clasificacion.toString());
    body.set('categoria', data.categoria.toString());
    body.set('precio', data.precio.toString());
    body.set('fechaLanzamiento', data.fechaLanzamiento);

    return this.http.post(
      `${API_BASE_URL}/actualizarJuego`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
    );
  }

  comprarJuego(nombreJuego: string, mail: string): Observable<string> {
    const params = new HttpParams()
      .set('juego', nombreJuego)
      .set('mail', mail);

    return this.http.post(
      `${API_BASE_URL}/registrarCompra`,
      params,
      { responseType: 'text' }
    );
  }

  instalarJuego(nombreJuego: string, mail: string): Observable<RespuestaInstalacion> {
    const params = new HttpParams()
      .set('juego', nombreJuego)
      .set('mail', mail);

    return this.http.post<RespuestaInstalacion>(
      `${API_BASE_URL}/instalarJuego`,
      params
    );
  }

  desinstalarJuego(idJuego: number, mail: string) {
    const body = new URLSearchParams();
    body.set('idJuego', idJuego.toString());
    body.set('mail', mail);

    return this.http.post<any>(
      `${API_BASE_URL}/desinstalarJuego`,
      body.toString(),
      {
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }
    );
  }

  subirImagenJuego(form: FormData): Observable<any> {
    return this.http.post(`${API_BASE_URL}/guardarImagenJuego`, form);
  }

  suspenderVenta(nombreJuego: string, idEmpresa: string): Observable<any> {
    const params = new HttpParams()
      .set('nombreJuego', nombreJuego)
      .set('idEmpresa', idEmpresa);

    return this.http.post(`${API_BASE_URL}/suspenderVenta`, params);
  }
}