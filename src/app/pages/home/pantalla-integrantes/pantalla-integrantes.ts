import { Component, OnInit ,ChangeDetectorRef} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { IntegranteGrupo, ObtenerIntegrantesGrupoService } from '../../../services/obtener-integrantes-grupo-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-integrantes-grupo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pantalla-integrantes.html',
  styleUrl: './pantalla-integrantes.css'
})
export class IntegrantesGrupoComponent implements OnInit {

  integrantes: IntegranteGrupo[] = [];
  cargando = true;
  error: string | null = null;
  idGrupo!: number;

  constructor(
    private route: ActivatedRoute,
    private integrantesService: ObtenerIntegrantesGrupoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.idGrupo = Number(this.route.snapshot.paramMap.get('idGrupo'));

    if (!this.idGrupo) {
      this.error = 'Grupo inválido';
      this.cargando = false;
      this.cdr.detectChanges();
      return;
    }

    this.integrantesService.obtenerIntegrantes(this.idGrupo).subscribe({
      next: data => {
        this.integrantes = data;
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Error al cargar integrantes del grupo';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }
  verPerfil(mail: string) {
    this.router.navigate(['/app/perfil-usuario', mail]);
  }

}
