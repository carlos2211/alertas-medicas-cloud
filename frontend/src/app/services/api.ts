import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { apiConfig } from '../auth-config';
import { HttpClient, HttpHeaders } from '@angular/common/http';

export interface Paciente {
  id?: number;
  nombre: string;
  apellido: string;
  rut: string;
  fechaNacimiento?: string;
  habitacion?: string;
  diagnostico?: string;
  creadoEn?: string;
}

export interface Alerta {
  id?: number;
  pacienteId: number;
  tipo: string;
  valorMedido: number;
  unidad: string;
  severidad: string;
  estado?: string;
  creadaEn?: string;
  resueltaEn?: string;
  resueltaPor?: string;
}

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  // API Gateway: se mantiene para pacientes
  private base = apiConfig.baseUrl;

  // Ngrok directo: se usa temporalmente para alertas por problema de CORS en API Gateway
  private alertasBase = 'https://numbing-untaken-divisibly.ngrok-free.dev';

  // Header necesario para evitar la pantalla/interferencia de ngrok en navegador
  private ngrokOptions = {
    headers: new HttpHeaders({
      'ngrok-skip-browser-warning': 'true',
    }),
  };

  constructor(private http: HttpClient) {}

  // =========================
  // PACIENTES - API GATEWAY
  // =========================

  getPacientes(): Observable<Paciente[]> {
    return this.http.get<Paciente[]>(`${this.base}/api/v1/pacientes`);
  }

  getPaciente(id: number): Observable<Paciente> {
    return this.http.get<Paciente>(`${this.base}/api/v1/pacientes/${id}`);
  }

  crearPaciente(paciente: Paciente): Observable<Paciente> {
    return this.http.post<Paciente>(
      `${this.base}/api/v1/pacientes`,
      paciente
    );
  }

  actualizarPaciente(id: number, paciente: Paciente): Observable<Paciente> {
    return this.http.put<Paciente>(
      `${this.base}/api/v1/pacientes/${id}`,
      paciente
    );
  }

  eliminarPaciente(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/api/v1/pacientes/${id}`);
  }

  // =========================
  // ALERTAS - NGROK DIRECTO
  // =========================

  getTodasAlertas(): Observable<Alerta[]> {
    return this.http.get<Alerta[]>(
      `${this.alertasBase}/api/v1/alertas`,
      this.ngrokOptions
    );
  }

  getAlertas(estado: string = 'ACTIVA'): Observable<Alerta[]> {
    return this.http.get<Alerta[]>(
      `${this.alertasBase}/api/v1/alertas?estado=${estado}`,
      this.ngrokOptions
    );
  }

  crearAlerta(alerta: Alerta): Observable<Alerta> {
    return this.http.post<Alerta>(
      `${this.alertasBase}/api/v1/alertas`,
      alerta,
      this.ngrokOptions
    );
  }

  resolverAlerta(id: number): Observable<Alerta> {
    return this.http.put<Alerta>(
      `${this.alertasBase}/api/v1/alertas/${id}/resolver`,
      {},
      this.ngrokOptions
    );
  }

  eliminarAlerta(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.alertasBase}/api/v1/alertas/${id}`,
      this.ngrokOptions
    );
  }
}