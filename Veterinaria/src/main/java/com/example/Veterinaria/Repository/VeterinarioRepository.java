package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {

    @Query(value = """
      SELECT v.id AS id_veterinario, v.nombre, COUNT(*) AS atendidas
      FROM cita c
      JOIN veterinario v ON v.id = c.id_veterinario
      WHERE c.estado = 'Atendida'
      GROUP BY v.id, v.nombre
      ORDER BY atendidas DESC
      LIMIT 10
      """, nativeQuery = true)
    List<Map<String,Object>> topAtenciones();
}

