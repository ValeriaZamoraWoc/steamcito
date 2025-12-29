import { CommonModule } from '@angular/common';
import { Component, OnInit ,ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { Juego, obtenerTodosLosJuegosService } from '../../../services/obtener-todos-los-juegos';
import { BannerHomeComponent } from '../banner/banner';

@Component({
  selector: 'app-pantalla-principal-comun',
  standalone: true,
  imports: [CommonModule,BannerHomeComponent],
  templateUrl: './pantalla-principal-comun.html',
  styleUrl: './pantalla-principal-comun.css',
})
export class ComponentePantallaPrincipalComun implements OnInit {

  juegos: Juego[] = [];
  cargando = true;

  constructor(
    private juegosService: obtenerTodosLosJuegosService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.juegosService.obtenerTodos().subscribe(data => {
      this.juegos = data;
      this.cargando = false;
      this.cdr.detectChanges();
    });
  }

  verJuego(id: number) {
    this.router.navigate(['/app/juego', id]);
  }
}
