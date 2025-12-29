import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalificacionPersonal, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { LoginService } from '../../../../services/login-service';

@Component({
  selector: 'app-calificaciones-personales',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './calificaciones-personales-biblioteca.html',
styleUrl: '../opciones-reportes.css' 
})
export class CalificacionesPersonalesComponent implements OnInit {

  calificaciones: CalificacionPersonal[] = [];
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
      .obtenerReporte<CalificacionPersonal[]>(
        usuario.mail,
        'calificacionesPersonales'
      )
      .subscribe({
        next: resp => {
          this.calificaciones = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudieron cargar tus calificaciones';
          this.cargando = false;
        }
      });
  }
}
