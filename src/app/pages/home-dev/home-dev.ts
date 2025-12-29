import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../services/login-service';
import { CatalogoEmpresaService, JuegoEmpresa } from '../../services/obtener-catalogo-empresa-service';
import { RouterOutlet } from "@angular/router";

@Component({
  selector: 'app-home-dev',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './home-dev.html'
})
export class HomeDevComponent implements OnInit {
  ngOnInit(): void {
  }

}
