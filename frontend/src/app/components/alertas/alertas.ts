import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from '../navbar/navbar';
import { ApiService, Alerta, Paciente } from '../../services/api';

@Component({
  selector: 'app-alertas',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './alertas.html',
  styleUrl: './alertas.scss'
})
export class AlertasComponent implements OnInit {
  alertas: Alerta[] = [];
  pacientes: Paciente[] = [];
  cargando = true;
  mensaje = '';
  filtroEstado = 'ACTIVA';
  mostrarFormulario = false;

  nuevaAlerta: Alerta = {
    pacienteId: 0,
    tipo: '',
    valorMedido: 0,
    unidad: '',
    severidad: 'ALTA'
  };

  tiposAlerta = ['FRECUENCIA_CARDIACA', 'PRESION_ARTERIAL', 'TEMPERATURA', 'SATURACION_OXIGENO', 'GLUCOSA'];
  severidades = ['ALTA', 'MEDIA', 'BAJA'];

  constructor(private api: ApiService) {}

  ngOnInit() {
    this.cargarAlertas();
    this.api.getPacientes().subscribe(p => this.pacientes = p);
  }

  cargarAlertas() {
    this.cargando = true;
    this.api.getAlertas(this.filtroEstado).subscribe({
      next: (data) => {
        this.alertas = data;
        this.cargando = false;
      },
      error: () => {
        this.mensaje = 'Error al cargar alertas';
        this.cargando = false;
      }
    });
  }

  cambiarFiltro(estado: string) {
    this.filtroEstado = estado;
    this.cargarAlertas();
  }

  crearAlerta() {
    if (!this.nuevaAlerta.pacienteId || !this.nuevaAlerta.tipo) {
      this.mensaje = 'Paciente y tipo son obligatorios';
      return;
    }
    this.api.crearAlerta(this.nuevaAlerta).subscribe({
      next: () => {
        this.mensaje = 'Alerta creada correctamente';
        this.mostrarFormulario = false;
        this.nuevaAlerta = { pacienteId: 0, tipo: '', valorMedido: 0, unidad: '', severidad: 'ALTA' };
        this.cargarAlertas();
      },
      error: () => this.mensaje = 'Error al crear alerta'
    });
  }

  resolver(id: number) {
    if (confirm('¿Marcar esta alerta como resuelta?')) {
      this.api.resolverAlerta(id).subscribe({
        next: () => {
          this.mensaje = 'Alerta resuelta';
          this.cargarAlertas();
        },
        error: () => this.mensaje = 'Error al resolver'
      });
    }
  }

  eliminar(id: number) {
    if (confirm('¿Eliminar esta alerta?')) {
      this.api.eliminarAlerta(id).subscribe({
        next: () => {
          this.alertas = this.alertas.filter(a => a.id !== id);
          this.mensaje = 'Alerta eliminada';
        },
        error: () => this.mensaje = 'Error al eliminar'
      });
    }
  }

  getNombrePaciente(id: number): string {
    const p = this.pacientes.find(p => p.id === id);
    return p ? `${p.nombre} ${p.apellido}` : `Paciente ${id}`;
  }
}