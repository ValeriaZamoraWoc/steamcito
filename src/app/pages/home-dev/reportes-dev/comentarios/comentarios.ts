import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../../services/login-service';
import { ReportesEmpresaService } from '../../../../services/reportes-empresa-service';

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
    private loginService: LoginService,
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
}
