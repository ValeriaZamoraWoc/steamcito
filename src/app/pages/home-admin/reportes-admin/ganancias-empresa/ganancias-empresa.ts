import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface GananciaEmpresa {
  empresa: string;
  monto: number;
}

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ganancias-empresa.html',
  styleUrl: '../reportes-admin.css' 
})
export class GananciasEmpresaComponent implements OnInit {

  empresas: GananciaEmpresa[] = [];
  cargando = true;
  error: string | null = null;

  constructor(private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef,
    private jasper : JasperReportsService
    
  ) {}

  ngOnInit(): void {
    this.reportesService.obtenerReporte('gananciasPorEmpresa')
      .subscribe({
        next: (resp) => {
          this.empresas = resp.datos.map((fila: string[]) => ({
            empresa: fila[0],
            monto: Number(fila[1])
          }));
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Error al cargar ganancias por empresa';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  exportarJasper(): void {
    this.jasper.gananciasPorEmpresaAdmin().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'ganancias_por_empresa.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
        this.cdr.detectChanges();
      }
    });}
}
