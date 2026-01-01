import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from "@angular/router";
import { UserService } from '../../../services/user-service';
import { CompanyService, JuegoEmpresa, JuegoEmpresaPerfil } from '../../../services/company-service';

@Component({
  selector: 'app-catalogo-empresa',
  imports: [CommonModule, RouterOutlet],
  templateUrl: './catalogo-empresa.html',
  styleUrl: './catalogo-empresa.css',
})
export class CatalogoEmpresaComponent implements OnInit {

  catalogo: JuegoEmpresaPerfil[] = [];
  idEmpresa!: number;

  constructor(
    private perfilService: CompanyService,
    private router: Router,
    private loginService: UserService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const id = this.loginService.user()?.idEmpresa;

    if (id) {
      this.idEmpresa = id;
      this.perfilService
        .obtenerPerfilEmpresa(this.idEmpresa)
        .subscribe((resp: any) => {
          this.catalogo = resp.catalogo ?? [];
          this.cdr.detectChanges(); 
        });
    }
  }
  verDetalle(idJuego: number) {
    this.cdr.detectChanges();
    this.router.navigate([
      '/app/home-dev/juego',
      idJuego
    ]);
    this.cdr.detectChanges();
  }
}
