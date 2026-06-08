import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { forkJoin } from 'rxjs';
import { NavbarComponent } from '../navbar/navbar';
import { ApiService, Paciente, Alerta } from '../../services/api';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class DashboardComponent implements OnInit {
  totalPacientes = 0;
  alertasActivas = 0;
  alertasResueltas = 0;
  ultimasAlertas: any[] = [];

  constructor(private api: ApiService) {}

  ngOnInit(): void {
    this.cargarDashboard();
  }

  cargarDashboard(): void {
    forkJoin({
      pacientes: this.api.getPacientes(),
      activas: this.api.getAlertas('ACTIVA'),
      resueltas: this.api.getAlertas('RESUELTA'),
    }).subscribe({
      next: ({ pacientes, activas, resueltas }) => {
        this.totalPacientes = pacientes.length;

        const activasFiltradas = activas.filter(
          (alerta) => this.normalizarEstado(alerta.estado) === 'ACTIVA',
        );

        const resueltasFiltradas = resueltas.filter(
          (alerta) => this.normalizarEstado(alerta.estado) === 'RESUELTA',
        );

        this.alertasActivas = activasFiltradas.length;
        this.alertasResueltas = resueltasFiltradas.length;

        this.ultimasAlertas = activasFiltradas.slice(0, 5).map((alerta) => {
          const paciente = pacientes.find((p) => p.id === alerta.pacienteId);

          return {
            ...alerta,
            pacienteNombre: paciente
              ? `${paciente.nombre} ${paciente.apellido}`
              : `Paciente ${alerta.pacienteId}`,
            pacienteSala: paciente?.habitacion || 'Sin sala asignada',
          };
        });
      },
      error: (err) => {
        console.error('Error cargando dashboard:', err);
        this.totalPacientes = 0;
        this.alertasActivas = 0;
        this.alertasResueltas = 0;
        this.ultimasAlertas = [];
      },
    });
  }

  private normalizarEstado(estado: string | undefined | null): string {
    return String(estado || '')
      .trim()
      .toUpperCase();
  }
}
