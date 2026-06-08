import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar';
import { ApiService, Paciente } from '../../services/api';

@Component({
  selector: 'app-pacientes',
  standalone: true,
  imports: [CommonModule, RouterLink, NavbarComponent],
  templateUrl: './pacientes.html',
  styleUrl: './pacientes.scss'
})
export class PacientesComponent implements OnInit {
  pacientes: Paciente[] = [];
  cargando = true;
  mensaje = '';

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.cargarPacientes();
  }

  cargarPacientes() {
    this.cargando = true;
    this.api.getPacientes().subscribe({
      next: (data) => {
        this.pacientes = data;
        this.cargando = false;
      },
      error: () => {
        this.mensaje = 'Error al cargar pacientes';
        this.cargando = false;
      }
    });
  }

  eliminar(id: number) {
    if (confirm('¿Eliminar este paciente?')) {
      this.api.eliminarPaciente(id).subscribe({
        next: () => {
          this.pacientes = this.pacientes.filter(p => p.id !== id);
          this.mensaje = 'Paciente eliminado correctamente';
        },
        error: () => this.mensaje = 'Error al eliminar'
      });
    }
  }
}