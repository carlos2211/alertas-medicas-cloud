import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar';
import { ApiService, Paciente } from '../../services/api';

@Component({
  selector: 'app-paciente-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, NavbarComponent],
  templateUrl: './paciente-form.html',
  styleUrl: './paciente-form.scss'
})
export class PacienteFormComponent implements OnInit {
  paciente: Paciente = {
    nombre: '',
    apellido: '',
    rut: '',
    fechaNacimiento: '',
    habitacion: '',
    diagnostico: ''
  };
  esEdicion = false;
  cargando = false;
  error = '';

  constructor(
    private api: ApiService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.esEdicion = true;
      this.api.getPaciente(+id).subscribe({
        next: (p) => this.paciente = p,
        error: () => this.error = 'Error al cargar paciente'
      });
    }
  }

  guardar() {
    if (!this.paciente.nombre || !this.paciente.apellido || !this.paciente.rut) {
      this.error = 'Nombre, apellido y RUT son obligatorios';
      return;
    }
    this.cargando = true;
    if (this.esEdicion) {
      this.api.actualizarPaciente(this.paciente.id!, this.paciente).subscribe({
        next: () => this.router.navigate(['/pacientes']),
        error: () => { this.error = 'Error al actualizar'; this.cargando = false; }
      });
    } else {
      this.api.crearPaciente(this.paciente).subscribe({
        next: () => this.router.navigate(['/pacientes']),
        error: () => { this.error = 'Error al crear'; this.cargando = false; }
      });
    }
  }
}