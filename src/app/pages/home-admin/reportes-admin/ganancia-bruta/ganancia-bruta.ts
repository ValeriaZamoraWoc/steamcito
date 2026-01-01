import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface GananciaJuego {
  nombre: string;
  monto: number;
}

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ganancia-bruta.html',
  styleUrl: '../reportes-admin.css' 
})
export class GananciaBrutaComponent implements OnInit {

  ganancias: GananciaJuego[] = [];
  totalGeneral = 0;
  cargando = true;
  error: string | null = null;

  constructor(private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef,
    private jasper : JasperReportsService
  ) {}

  ngOnInit(): void {
    this.reportesService.obtenerReporte('gananciasGlobales')
      .subscribe({
        next: (resp) => {
          this.ganancias = resp.datos.map((fila: string[]) => ({
            nombre: fila[0],
            monto: Number(fila[1])
          }));

          this.totalGeneral = this.ganancias
            .reduce((acc, g) => acc + g.monto, 0);

          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Error al cargar ganancias globales';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  exportarJasper(): void {
    this.jasper.gananciasGlobalesAdmin().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'ganancias_globales.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
        this.cdr.detectChanges();
      }
    });
  }
}

