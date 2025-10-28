package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Citas futuras en estado Programada para un veterinario
    @Query(value = """
      SELECT c.*
      FROM cita c
      WHERE c.id_veterinario = :idVet
        AND c.estado = 'Programada'
        AND c.fecha_hora >= NOW()
      ORDER BY c.fecha_hora
      """, nativeQuery = true)
    List<Cita> pendientesPorVet(@Param("idVet") Long idVet);

    // Historial de una mascota con nombre del veterinario
    @Query(value = """
      SELECT c.fecha_hora, c.motivo, c.estado, v.nombre AS veterinario
      FROM cita c
      JOIN veterinario v ON v.id = c.id_veterinario
      WHERE c.id_mascota = :idMascota
      ORDER BY c.fecha_hora DESC
      """, nativeQuery = true)
    List<Map<String,Object>> historialPorMascota(@Param("idMascota") Long idMascota);
}

