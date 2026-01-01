import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../services/user-service';
import {
  ReportesEmpresaService,
  VentaEmpresa,
  ReporteEmpresaResponse
} from '../../../../services/reportes-empresa-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ventas.html',
   styleUrl: '../reportes-dev.css' 
})
export class ReporteVentasDevComponent implements OnInit {

  ventas: VentaEmpresa[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private loginService: UserService,
    private jasper : JasperReportsService,
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
      .obtenerReporte<VentaEmpresa[]>(idEmpresa, 'ventas')
      .subscribe({
        next: resp => {
          this.ventas = resp.datos;
          this.cargando = false;
        },
        error: () => {
          this.error = 'Error al cargar ventas';
          this.cargando = false;
        }
      });
  }

  exportarJasper() {
    const idEmpresa = this.loginService.getIdEmpresa();
    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      return;
    }
    this.jasper.reporteVentasEmpresa(idEmpresa).subscribe({
      next: (resp) => {
        const blob = new Blob([resp], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_ventas.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
      }
    });
  }
}


