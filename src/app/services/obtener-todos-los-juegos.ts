import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { API_BASE_URL } from "../core/api.config";
import { Injectable } from "@angular/core";

export interface Juego{
    id: number, nombreJuego: string, descripcion: string,  especificaciones:string, clasificacion: number, categoria: number,
  empresa: number, precio: number, enVenta: boolean, fechaLanzamiento: Date, urlImagen: string 
}
@Injectable({
  providedIn: 'root'
})
export class obtenerTodosLosJuegosService{
    constructor(private http: HttpClient){}

  obtenerTodos(): Observable<Juego[]> {
    return this.http.get<Juego[]>(
      `${API_BASE_URL}/obtenerJuegos`
    );
  }
}