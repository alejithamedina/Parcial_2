package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "mascota", indexes = @Index(name="idx_mascota_id_cliente", columnList="id_cliente"))
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 60)
    private String especie;

    @Column(length = 80)
    private String raza;

    @NotBlank
    @Column(nullable = false, length = 10)
    private String sexo;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false,
            foreignKey = @ForeignKey(name = "fk_mascota_cliente_1"))
    private Cliente cliente;

}
