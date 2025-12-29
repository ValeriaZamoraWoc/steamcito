import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginService } from '../../services/login-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-usuario',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login-usuario.html',
  styleUrl: './login-usuario.css'
})
export class LoginUsuario {

  mail = '';
  password = '';
  error = '';

  constructor(public loginService: LoginService,
    private router: Router
  ) {}

  async logear() {
    this.error = '';

    try {
      await this.loginService.login(this.mail, this.password);

      if (this.loginService.isDesarrollador()) {
        this.router.navigate(['/app/home-dev']);
      } else {
        this.router.navigate(['/app/home']);
      }

    } catch {
      this.error = 'Credenciales inválidas';
    }
  }

}
