import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClasificacionFavorita, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { UserService } from '../../../../services/user-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  selector: 'app-clasificaciones-favoritas',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './clasificaciones-favoritas.html',
styleUrl: '../opciones-reportes.css' 
})
export class ClasificacionesFavoritasComponent implements OnInit {

  clasificaciones: ClasificacionFavorita[] = [];
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
      .obtenerReporte<ClasificacionFavorita[]>(
        usuario.mail,
        'clasificacionesFavoritas'
      )
      .subscribe({
        next: resp => {
          this.clasificaciones = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudieron cargar tus clasificaciones favoritas';
          this.cargando = false;
        }
      });
  }
  exportarJasper(): void {
    const usuario = this.loginService.user();
    if (usuario?.mail) {
      this.jasper.clasificacionesPersonalesFavoritas(usuario.mail).subscribe({
        next: (blob: Blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = 'clasificaciones-favoritas.pdf';
          document.body.appendChild(a);
          a.click();
          window.URL.revokeObjectURL(url);
        },
        error: () => {
          console.error('Error al exportar el reporte');
        }
      });
    }
  }}