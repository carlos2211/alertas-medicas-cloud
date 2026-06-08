import { Routes } from '@angular/router';
import { MsalGuard } from '@azure/msal-angular';
import { LoginComponent } from './components/login/login';
import { DashboardComponent } from './components/dashboard/dashboard';
import { PacientesComponent } from './components/pacientes/pacientes';
import { PacienteFormComponent } from './components/paciente-form/paciente-form';
import { AlertasComponent } from './components/alertas/alertas';
import { AuthCallbackComponent } from './components/auth-callback/auth-callback';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'auth-callback', component: AuthCallbackComponent },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'pacientes',
    component: PacientesComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'pacientes/nuevo',
    component: PacienteFormComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'pacientes/editar/:id',
    component: PacienteFormComponent,
    canActivate: [MsalGuard],
  },
  {
    path: 'alertas',
    component: AlertasComponent,
    canActivate: [MsalGuard],
  },
  { path: '**', redirectTo: '/login' },
];
