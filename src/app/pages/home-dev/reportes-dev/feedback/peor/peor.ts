import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../../services/user-service';
import {
  ReportesEmpresaService,
  JuegoCalificado,
  ReporteEmpresaResponse
} from '../../../../../services/reportes-empresa-service';
import { JasperReportsService } from '../../../../../services/jasper-reports-service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './peor.html',
  styleUrl: '../../reportes-dev.css'
})
export class ReportePeorCalificadosDevComponent implements OnInit {

  juegos: JuegoCalificado[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private loginService: UserService,
    private reportesService: ReportesEmpresaService,
    private jasper: JasperReportsService
  ) {}

  ngOnInit(): void {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<JuegoCalificado[]>(idEmpresa, 'peorcalificados')
      .subscribe({
        next: (resp: ReporteEmpresaResponse<JuegoCalificado[]>) => {
          this.juegos = resp.datos;
          this.cargando = false;
        },
        error: () => {
          this.error = 'Error al cargar feedback negativo';
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

    this.jasper.juegosPeorCalificadosEmpresa(idEmpresa).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Reporte_Peor_Calificados.pdf';
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
