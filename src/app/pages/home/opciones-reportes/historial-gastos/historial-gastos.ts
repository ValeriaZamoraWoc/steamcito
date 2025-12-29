import { Component, OnInit,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../../../services/login-service';
import { HistorialGastosService } from '../../../../services/historial-gastos-service';

@Component({
  selector: 'app-historial-gastos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './historial-gastos.html',
  styleUrl: '../opciones-reportes.css' 
})
export class HistorialGastosComponent implements OnInit {

  historial: string[] = [];
  cargando = true;
  error: string | null = null;

  constructor(
    private historialService: HistorialGastosService,
    private loginService: LoginService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      this.cargando = false;
      this.cdr.detectChanges();
      return;
    }

    this.historialService.obtenerHistorial(usuario.mail).subscribe({
      next: data => {
        this.historial = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al obtener historial de gastos';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }
}
