import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';

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
    private cdr: ChangeDetectorRef
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
}
