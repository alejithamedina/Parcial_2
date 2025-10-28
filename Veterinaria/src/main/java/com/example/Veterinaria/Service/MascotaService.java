package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Mascota;
import com.example.Veterinaria.Repository.ClienteRepository;
import com.example.Veterinaria.Repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MascotaService {

    private final MascotaRepository repo;
    private final ClienteRepository clienteRepo;

    public List<Mascota> listar() { return repo.findAll(); }

    public Optional<Mascota> obtener(Long id) { return repo.findById(id); }

    @Transactional
    public Mascota crear(Mascota m) {
        Long idCliente = (m.getCliente()!=null) ? m.getCliente().getId() : null;
        if (idCliente == null || !clienteRepo.existsById(idCliente)) {
            throw new IllegalArgumentException("Cliente no existe");
        }
        return repo.save(m);
    }

    @Transactional
    public Optional<Mascota> actualizar(Long id, Mascota m) {
        return repo.findById(id).map(db -> {
            db.setNombre(m.getNombre());
            db.setEspecie(m.getEspecie());
            db.setRaza(m.getRaza());
            db.setSexo(m.getSexo());
            db.setFechaNac(m.getFechaNac()); // asegúrate que el getter se llama así
            if (m.getCliente()!=null && m.getCliente().getId()!=null &&
                    clienteRepo.existsById(m.getCliente().getId())) {
                db.setCliente(m.getCliente());
            }
            return repo.save(db);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }

    public List<Mascota> porDocumentoCliente(String documento) {
        return repo.findByDocumentoCliente(documento);
    }

    public List<MascotaRepository.EspecieCount> totalesPorEspecie() {
        return repo.totalPorEspecie();
    }
}


