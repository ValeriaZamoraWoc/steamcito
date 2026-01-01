import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface UsuarioCompras {
  usuario: string;
  totalJuegos: number;
}

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './usuarios-compras.html',
  styleUrl: '../reportes-admin.css' 
})
export class UsuariosComprasComponent implements OnInit {

  usuarios: UsuarioCompras[] = [];
  cargando = true;
  error: string | null = null;

  constructor(private reportesService: ReportesAdminService,
    private cdr: ChangeDetectorRef,
        private jasper : JasperReportsService
  ) {}

  ngOnInit(): void {
    this.reportesService.obtenerReporte('jugadoresConMasJuegos')
      .subscribe({
        next: (resp) => {
          this.usuarios = resp.datos.map((fila: string[]) => ({
            usuario: fila[0],
            totalJuegos: Number(fila[1])
          }));
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.error = 'Error al cargar usuarios';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  exportarJasper(): void {
    this.jasper.jugadoresConMasJuegosAdmin().subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'jugadores_con_mas_juegos.pdf';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Error al exportar el reporte:', error);
      }
    });
  }
}
