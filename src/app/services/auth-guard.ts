import { inject } from '@angular/core';
import { Router, UrlTree } from '@angular/router';
import { UserService } from './user-service';

export const authGuard = (): boolean | UrlTree => {
  const userService = inject(UserService);
  const router = inject(Router);

  if (!userService.isLogged()) {
    return router.createUrlTree(['/login']);
  }

  return true;
};
