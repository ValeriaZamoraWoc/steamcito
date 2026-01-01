import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JuegoPrestado, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { UserService } from '../../../../services/user-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

@Component({
  selector: 'app-prestados-mas-tiempo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prestados-mas-tiempo.html',
styleUrl: '../opciones-reportes.css' 
})
export class PrestadosMasTiempoComponent implements OnInit {

  juegos: JuegoPrestado[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private reportesService: ReportesUsuarioComunService,
    private loginService: UserService,
    private jasper: JasperReportsService
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      return;
    }

    this.reportesService
      .obtenerReporte<JuegoPrestado[]>(
        usuario.mail,
        'prestadosMasTiempo'
      )
      .subscribe({
        next: resp => {
          this.juegos = resp.data;
          this.cargando = false;
        },
        error: () => {
          this.error = 'No se pudo cargar el reporte de juegos prestados';
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

    this.jasper.prestadosMasJugados(usuario.mail).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'prestados_mas_tiempo.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'No se pudo exportar el reporte';
      }
    });
  }
}
