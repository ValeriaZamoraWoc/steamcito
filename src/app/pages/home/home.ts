import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentePantallaPrincipalComun } from './pantalla-principal-comun/pantalla-principal-comun';
import { UserService } from '../../services/user-service';
import { BannerHomeComponent } from './banner/banner';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule,
    BannerHomeComponent,
    ComponentePantallaPrincipalComun],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class ComponenteHome {
    constructor(public userService: UserService) {}
}
