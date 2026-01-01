import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JuegoCalificadoUsuario, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { UserService } from '../../../../services/user-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  selector: 'app-prestados-mejor-calificados',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prestados-mejor-calificados.html',
styleUrl: '../opciones-reportes.css' 
})
export class PrestadosMejorCalificadosComponent implements OnInit {

  juegos: JuegoCalificadoUsuario[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private reportesService: ReportesUsuarioComunService,
    private jasper: JasperReportsService,
    private cdr: ChangeDetectorRef,
    private loginService: UserService
  ) {}

  exportarJasper(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.jasper.prestadosMejorCalificados(usuario.mail).subscribe({
        next: (blob) => {
          const url = window.URL.createObjectURL(blob);
          const a = document.createElement('a');
          a.href = url;
          a.download = `juegos_prestados_mejor_calificados_${usuario.mail}.pdf`;
          a.click();
          window.URL.revokeObjectURL(url);
        },
        error: () => {
          this.error = 'Error al exportar reporte';
          this.cdr.detectChanges();
        }
    });
  }

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<JuegoCalificadoUsuario[]>(
        usuario.mail,
        'prestadosMejorCalificados'
      )
      .subscribe({
        next: resp => {
          this.juegos = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudo cargar el reporte';
          this.cargando = false;
        }
      });
  }
}
