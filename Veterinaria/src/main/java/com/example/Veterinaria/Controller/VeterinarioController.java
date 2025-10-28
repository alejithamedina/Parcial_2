package com.example.Veterinaria.Controller;

import com.example.Veterinaria.Model.Veterinario;
import com.example.Veterinaria.Service.VeterinarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService service;

    @GetMapping
    public List<Veterinario> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ResponseEntity<Veterinario> obtener(@PathVariable Long id) {
        return service.obtener(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Veterinario v) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(v));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Veterinario v) {
        try {
            return service.actualizar(id, v)
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

    @GetMapping("/top-atenciones")
    public List<Map<String,Object>> topAtenciones() { return service.topAtenciones(); }
}

