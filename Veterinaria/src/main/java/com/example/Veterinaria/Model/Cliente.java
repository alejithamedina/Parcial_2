package com.example.Veterinaria.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "cliente",
        uniqueConstraints = @UniqueConstraint(columnNames = "documento"))
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 40)
    private String documento;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String telefono;

    @Email
    @Column(length = 150)
    private String correo;

    @Column(length = 200)
    private String direccion;


}
