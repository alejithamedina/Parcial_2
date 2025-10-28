package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Cita;
import com.example.Veterinaria.Service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService service;

    @GetMapping
    public List<Cita> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtener(@PathVariable Long id) {
        return service.obtener(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Cita c) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(c));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Cita c) {
        try {
            return service.actualizar(id, c)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return service.eliminar(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/pendientes")
    public List<Cita> pendientes(@RequestParam("idVet") Long idVet) {
        return service.pendientesPorVet(idVet);
    }

    @GetMapping("/mascota/{idMascota}/historial")
    public List<Map<String,Object>> historial(@PathVariable Long idMascota) {
        return service.historialPorMascota(idMascota);
    }
}
