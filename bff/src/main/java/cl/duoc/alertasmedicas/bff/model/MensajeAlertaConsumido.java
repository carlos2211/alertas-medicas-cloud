package cl.duoc.alertasmedicas.bff.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mensajes_alerta_consumidos")
public class MensajeAlertaConsumido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alerta_id")
    private Long alertaId;

    @Column(name = "paciente_id")
    private Long pacienteId;

    private String tipo;

    @Column(name = "valor_medido")
    private Double valorMedido;

    private String unidad;

    private String severidad;

    private String estado;

    @Column(name = "mensaje_original", length = 4000)
    private String mensajeOriginal;

    @Column(name = "consumido_en")
    private LocalDateTime consumidoEn;

    @PrePersist
    public void prePersist() {
        consumidoEn = LocalDateTime.now();
    }
}