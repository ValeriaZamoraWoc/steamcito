import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-reportes-desarrollador',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './reportes-dev.html',
  styleUrl: './reportes-dev.css'
})
export class ReportesDesarrolladorComponent {

  constructor(private router: Router) {}

  irA(tipo: string) {
    switch (tipo) {
      case 'ventas':
        this.router.navigate(['/app/home-dev/reportes/ventas']);
        break;

      case 'mejorcalificados':
        this.router.navigate(['/app/home-dev/reportes/feedback/mejor']);
        break;

      case 'peorcalificados':
        this.router.navigate(['/app/home-dev/reportes/feedback/peor']);
        break;

      case 'top5':
        this.router.navigate(['/app/home-dev/reportes/top5']);
        break;
      case 'comentarios':
        this.router.navigate(['/app/home-dev/reportes/comentarios']);
        break;
      }
  }
}
