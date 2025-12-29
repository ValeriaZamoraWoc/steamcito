import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api.config';

export interface Prestamo {
  mailDueno: string;
  mailPrestamista: string;
  idJuego: number;
  fechaPrestamo: string;
  fechaDevolucion?: string;
  devuelto: boolean;
}
export interface RespuestaDevolucion {
  exito: boolean;
  mensaje: string;
  mailPrestamista: string;
  fechaDevolucion: string;
}
@Injectable({ providedIn: 'root' })
export class PrestamosService {

  constructor(private http: HttpClient) {}

    tienePrestamosActivos(mail: string) {
    return this.http.get<{
        exito: boolean;
        mail: string;
        tienePrestamosActivos: boolean;
    }>(`${API_BASE_URL}/tienePrestamosActivos`, {
        params: { mail }
    });
    }
  registrarPrestamo(mailDueno: string, mailPrestamista: string, idJuego: number, fecha: string) {
    const body = new URLSearchParams();
    body.set('mailDueno', mailDueno);
    body.set('mailPrestamista', mailPrestamista);
    body.set('idJuego', idJuego.toString());
    body.set('fecha', fecha);

    return this.http.post(`${API_BASE_URL}/registrarPrestamo`, body.toString(), {
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    });
  }
devolverPrestamo(mailPrestamista: string) {
  const body = new URLSearchParams();
  body.set('mailPrestamista', mailPrestamista);

  return this.http.post<RespuestaDevolucion>(
    `${API_BASE_URL}/devolverPrestamo`,
    body.toString(),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  );
}

}