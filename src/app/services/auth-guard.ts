import { inject } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { LoginService } from './login-service';

export const authGuard = (): boolean | UrlTree => {
  const login = inject(LoginService);
  const router = inject(Router);

  if (!login.isLogged()) {
    return router.createUrlTree(['/login']);
  }

  return true;
};
