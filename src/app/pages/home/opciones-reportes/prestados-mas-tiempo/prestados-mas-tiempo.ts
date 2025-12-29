import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JuegoPrestado, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { LoginService } from '../../../../services/login-service';

@Component({
  selector: 'app-prestados-mas-tiempo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prestados-mas-tiempo.html',
styleUrl: '../opciones-reportes.css' 
})
export class PrestadosMasTiempoComponent implements OnInit {

  juegos: JuegoPrestado[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private reportesService: ReportesUsuarioComunService,
    private loginService: LoginService
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<JuegoPrestado[]>(
        usuario.mail,
        'prestadosMasTiempo'
      )
      .subscribe({
        next: resp => {
          this.juegos = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudo cargar el reporte de juegos prestados';
          this.cargando = false;
        }
      });
  }
}
