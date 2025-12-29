import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import {VerPerfilEmpresaService,EmpresaPerfil,JuegoEmpresaPerfil,PerfilEmpresaResponse} from '../../../services/ver-perfil-empresa-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ver-perfil-empresa',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ver-perfil-empresa.html',
  styleUrl: './ver-perfil-empresa.css'
})
export class VerPerfilEmpresaComponent implements OnInit {

  empresa!: EmpresaPerfil;
  catalogo: JuegoEmpresaPerfil[] = [];
  totalJuegos = 0;

  cargando = true;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private perfilEmpresaService: VerPerfilEmpresaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    const idEmpresa = Number(
      this.route.snapshot.paramMap.get('idEmpresa')
    );

    if (!idEmpresa) {
      this.error = 'Empresa inválida';
      this.cargando = false;
      return;
    }

    this.perfilEmpresaService.obtenerPerfilEmpresa(idEmpresa).subscribe({
      next: (data) => {

        if (!('empresa' in data)) {
          this.empresa = data;
        } else {
          const resp = data as PerfilEmpresaResponse;
          this.empresa = resp.empresa;
          this.catalogo = resp.catalogo ?? [];
          this.totalJuegos = resp.totalJuegos ?? 0;
        }

        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar el perfil de la empresa';
        this.cargando = false;
      }
    });
  }

}
