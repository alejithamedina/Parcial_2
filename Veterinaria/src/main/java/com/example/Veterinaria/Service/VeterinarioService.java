package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Veterinario;
import com.example.Veterinaria.Repository.VeterinarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VeterinarioService {

    private final VeterinarioRepository repo;

    public List<Veterinario> listar() { return repo.findAll(); }

    public Optional<Veterinario> obtener(Long id) { return repo.findById(id); }

    @Transactional
    public Veterinario crear(Veterinario v) { return repo.save(v); }

    @Transactional
    public Optional<Veterinario> actualizar(Long id, Veterinario v) {
        return repo.findById(id).map(db -> {
            db.setNombre(v.getNombre());
            db.setEspecialidad(v.getEspecialidad());
            db.setRegistroProfesional(v.getRegistroProfesional());
            return repo.save(db);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<Map<String,Object>> topAtenciones() { return repo.topAtenciones(); }
}

