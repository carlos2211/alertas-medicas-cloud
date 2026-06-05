package cl.duoc.alertasmedicas.bff.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

// ──────────────────────────────────────────────────────────
// DTO Paciente
// ──────────────────────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PacienteRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El RUT es obligatorio")
    private String rut;

    private String habitacion;
    private String diagnostico;
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class PacienteResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String rut;
    private String habitacion;
    private String diagnostico;
    private LocalDateTime creadoEn;
}

// ──────────────────────────────────────────────────────────
// DTO Alerta
// ──────────────────────────────────────────────────────────
@Data @Builder @NoArgsConstructor @AllArgsConstructor
class AlertaRequestDTO {
    @NotNull(message = "El id del paciente es obligatorio")
    private Long pacienteId;

    @NotBlank(message = "El tipo de alerta es obligatorio")
    private String tipo;           // ej: FRECUENCIA_CARDIACA, PRESION_ARTERIAL, SpO2

    @NotNull(message = "El valor medido es obligatorio")
    private Double valorMedido;

    @NotBlank(message = "La unidad es obligatoria")
    private String unidad;         // ej: bpm, mmHg, %

    @NotBlank(message = "La severidad es obligatoria")
    private String severidad;      // BAJA, MEDIA, ALTA, CRITICA
}

@Data @Builder @NoArgsConstructor @AllArgsConstructor
class AlertaResponseDTO {
    private Long id;
    private Long pacienteId;
    private String nombrePaciente;
    private String tipo;
    private Double valorMedido;
    private String unidad;
    private String severidad;
    private String estado;         // ACTIVA, RESUELTA
    private LocalDateTime creadaEn;
    private LocalDateTime resueltaEn;
    private String resueltaPor;    // sub (claim del usuario Azure AD B2C)
}
