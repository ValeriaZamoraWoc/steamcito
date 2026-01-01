import { Component, OnInit,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../../../services/user-service';
import { HistorialGastosService } from '../../../../services/historial-gastos-service';
import { JasperReportsService } from '../../../../services/jasper-reports-service';

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
    private jasper: JasperReportsService,
    private loginService: UserService,
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

  exportarJasper(): void {
    const usuario = this.loginService.user();

    if (!usuario?.mail) {
      this.error = 'Usuario no autenticado';
      return;
    }

    this.jasper.historialDeGastos(usuario.mail).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'historial_de_gastos.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.error = 'Error al exportar el historial de gastos';
      }
    });
}}
