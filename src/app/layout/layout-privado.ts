import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, Router, RouterModule } from '@angular/router';
import { UserService } from '../services/user-service';

@Component({
  selector: 'app-private-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink,RouterModule],
  templateUrl: './layout-privado.html',
  styleUrl: './layout-privado.css'
})
export class ComponenteLayoutPrivado {

  constructor(
    public loginService: UserService,
    private router: Router
  ) {}

  logout() {
    this.loginService.logout();
    this.router.navigate(['/login']);
  }
}
