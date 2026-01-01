import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../services/user-service';
import { ReportesEmpresaService } from '../../../../services/reportes-empresa-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

interface ComentarioReporte {
  id: number;
  comentario: string;
  juego: string;
  usuario: string;
  calificacion: string;
}

@Component({
  selector: 'app-reporte-comentarios-dev',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comentarios.html',
   styleUrl: '../reportes-dev.css' 
})
export class ReporteComentariosDevComponent implements OnInit {

  comentarios: ComentarioReporte[] = [];
  error = '';

  constructor(
    private loginService: UserService,
    private jasper : JasperReportsService,
    private reportesService: ReportesEmpresaService
  ) {}

  ngOnInit() {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      return;
    }

    this.reportesService.obtenerMejoresComentarios(idEmpresa)
      .subscribe({
        next: res => {
          this.comentarios = res.datos.map((fila: any[]) => ({
            id: Number(fila[0]),
            comentario: fila[1],
            juego: fila[2],
            usuario: fila[3],
            calificacion: fila[4]
          }));
        },
        error: () => {
          this.error = 'Error al cargar comentarios';
        }
      });
  }

  exportarJasper() {
    const idEmpresa = this.loginService.getIdEmpresa();

    if (!idEmpresa) {
      this.error = 'No se pudo obtener la empresa';
      return;
    }
    this.jasper.comentariosMejorCalificadosEmpresa(idEmpresa).subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'comentarios_mejor_calificados.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
      }
    });
  }
}
