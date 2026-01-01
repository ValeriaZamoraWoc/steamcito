import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../services/user-service';
import {
  ReportesEmpresaService,
  TopJuego,
  ReporteEmpresaResponse
} from '../../../../services/reportes-empresa-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './top-5.html',
   styleUrl: '../reportes-dev.css' 
})
export class ReporteTop5DevComponent implements OnInit {

  top5: TopJuego[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private loginService: UserService,
    private jasper: JasperReportsService,
    private reportesService: ReportesEmpresaService
  ) {}

  ngOnInit(): void {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<TopJuego[]>(idEmpresa, 'top5')
      .subscribe({
        next: (resp: ReporteEmpresaResponse<TopJuego[]>) => {
          this.top5 = resp.datos;
          this.cargando = false;
        },
        error: () => {
          this.error = 'Error al cargar el Top 5';
          this.cargando = false;
        }
      });
  }

  exportarJasper(): void {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      return;
    }

    this.jasper.top5JuegosEmpresa(idEmpresa).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'top_5_juegos.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
      }
    });
  }
}