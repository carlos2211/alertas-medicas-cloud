package cl.duoc.alertasmedicas.bff.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alertas")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "valor_medido")
    private Double valorMedido;

    private String unidad;
    private String severidad;

    @Column(nullable = false)
    private String estado = "ACTIVA";

    @Column(name = "creada_en")
    private LocalDateTime creadaEn;

    @Column(name = "resuelta_en")
    private LocalDateTime resueltaEn;

    @Column(name = "resuelta_por")
    private String resueltaPor;

    @PrePersist
    public void prePersist() {
        creadaEn = LocalDateTime.now();
        if (estado == null) estado = "ACTIVA";
    }
}