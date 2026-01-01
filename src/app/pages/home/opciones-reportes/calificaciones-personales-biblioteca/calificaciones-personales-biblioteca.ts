import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalificacionPersonal, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { UserService } from '../../../../services/user-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

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
    private jasper: JasperReportsService,
    private loginService: UserService
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

  exportarJasper(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.jasper
      .calificacionesPersonales(usuario.mail)
      .subscribe({
        next: blob => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'calificaciones_personales_biblioteca.pdf';
          document.body.appendChild(a);
          a.click();
          document.body.removeChild(a);
          window.URL.revokeObjectURL(url);
        },
        error: () => {
          this.error = 'No se pudo exportar el reporte';
        }
      });
  }
}
