package com.example.Veterinaria.Repository;

import com.example.Veterinaria.Model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    @Query(value = """
      SELECT m.*
      FROM mascota m
      JOIN cliente c ON c.id = m.id_cliente
      WHERE c.documento = :documento
      """, nativeQuery = true)
    List<Mascota> findByDocumentoCliente(@Param("documento") String documento);

    interface EspecieCount {
        String getEspecie();
        Long getTotal();
    }

    @Query(value = """
      SELECT especie AS especie, COUNT(*) AS total
      FROM mascota
      GROUP BY especie
      ORDER BY total DESC
      """, nativeQuery = true)
    List<EspecieCount> totalPorEspecie();
}
