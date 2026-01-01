import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JuegoCalificadoUsuario, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { UserService } from '../../../../services/user-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  selector: 'app-mejores-calificados-biblioteca',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mejor-calificados-biblioteca.html',
styleUrl: '../opciones-reportes.css' 
})
export class MejoresCalificadosBibliotecaComponent implements OnInit {

  juegos: JuegoCalificadoUsuario[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private reportesService: ReportesUsuarioComunService,
    private jasper: JasperReportsService,
    private loginService: UserService
  ) {}

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
        'mejoresCalificadosBiblioteca'
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

  exportarJasper(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.jasper.juegosMejorCalificadosUsuario(usuario.mail).subscribe({
      next: blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'mejores_calificados_biblioteca.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el reporte';
      }
    });
  }
}
