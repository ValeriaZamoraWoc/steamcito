import { CommonModule } from '@angular/common';
import { Component, OnInit ,ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { Juego, GameService } from '../../../services/game-service';
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
    private juegosService: GameService,
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
