// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { ProductAdminComponent } from './pages/admin/product-admin.component';
import { RoleGuard } from './auth/role.guard';

export const appRoutes: Routes = [
  { path: '', component: ProductListComponent },

  {
    path: 'login',
    loadComponent: () =>
      import('./auth/login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () =>
      import('./auth/register/register.component').then((m) => m.RegisterComponent),
  },
  {
    path: 'admin/products',
    component: ProductAdminComponent,
    canActivate: [RoleGuard],
    data: { role: 'ADMIN' }
  },
];
