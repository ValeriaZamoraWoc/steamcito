import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, Router, RouterModule } from '@angular/router';
import { LoginService } from '../services/login-service';

@Component({
  selector: 'app-private-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink,RouterModule],
  templateUrl: './layout-privado.html',
  styleUrl: './layout-privado.css'
})
export class ComponenteLayoutPrivado {

  constructor(
    public loginService: LoginService,
    private router: Router
  ) {}

  logout() {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
