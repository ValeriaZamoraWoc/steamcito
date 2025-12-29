import { Component, OnInit,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesAdminService } from '../../../../services/reportes-admin-service';

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
    private cdr: ChangeDetectorRef
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
}
