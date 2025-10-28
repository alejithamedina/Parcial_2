package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table(name = "cita",
        indexes = {
                @Index(name = "idx_cita_vet_estado_fecha", columnList = "id_veterinario, estado, fecha_hora"),
                @Index(name = "idx_cita_id_mascota", columnList = "id_mascota")
        })
public class Cita {
    public enum Estado { Programada, Atendida, Cancelada }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private java.time.LocalDateTime fechaHora;

    @Column(nullable = false, length = 200)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mascota", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_veterinario", nullable = false)
    private Veterinario veterinario;
}

