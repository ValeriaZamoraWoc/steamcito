import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JuegoCalificadoUsuario, ReportesUsuarioComunService } from '../../../../services/reportes-usuario-comun-service';
import { LoginService } from '../../../../services/login-service';

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
    private loginService: LoginService
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
}
