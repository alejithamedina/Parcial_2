package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "veterinario",
        uniqueConstraints = @UniqueConstraint(columnNames = "registro_profesional"))
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String especialidad;

    @NotBlank
    @Column(name = "registro_profesional", nullable = false, length = 60, unique = true)
    private String registroProfesional;


}

