import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user-service';
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

  constructor(public userService: UserService,
    private router: Router
  ) {}

  async logear() {
    this.error = '';

    try {
      await this.userService.login(this.mail, this.password);

      if (this.userService.isDesarrollador()) {
        this.router.navigate(['/app/home-dev']);
      } else {
        this.router.navigate(['/app/home']);
      }

    } catch {
      this.error = 'Credenciales inválidas';
    }
  }
irRegistro() {
  this.router.navigate(['registro-usuario']);
}

}
