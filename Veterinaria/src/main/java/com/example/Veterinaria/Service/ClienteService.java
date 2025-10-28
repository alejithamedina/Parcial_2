package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Cliente;
import com.example.Veterinaria.Repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository repo;

    public List<Cliente> listar() { return repo.findAll(); }

    public Optional<Cliente> obtener(Long id) { return repo.findById(id); }

    @Transactional
    public Cliente crear(Cliente c) {
        if (c.getDocumento()!=null && repo.existsByDocumento(c.getDocumento())) {
            throw new IllegalArgumentException("Documento ya existe");
        }
        return repo.save(c);
    }

    @Transactional
    public Optional<Cliente> actualizar(Long id, Cliente c) {
        return repo.findById(id).map(db -> {
            if (c.getDocumento()!=null && !c.getDocumento().equals(db.getDocumento())
                    && repo.existsByDocumento(c.getDocumento())) {
                throw new IllegalArgumentException("Documento ya existe");
            }
            db.setNombre(c.getNombre());
            db.setDocumento(c.getDocumento());
            db.setTelefono(c.getTelefono());
            db.setCorreo(c.getCorreo());
            db.setDireccion(c.getDireccion());
            return repo.save(db);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}


