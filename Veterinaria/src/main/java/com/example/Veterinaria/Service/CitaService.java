package com.example.Veterinaria.Service;

import com.example.Veterinaria.Model.Cita;
import com.example.Veterinaria.Model.Mascota;
import com.example.Veterinaria.Model.Veterinario;
import com.example.Veterinaria.Repository.CitaRepository;
import com.example.Veterinaria.Repository.MascotaRepository;
import com.example.Veterinaria.Repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaService(CitaRepository citaRepository,
                       MascotaRepository mascotaRepository,
                       VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    // CRUD básico
    @Transactional
    public Cita guardar(Cita cita) {
        validarFKs(cita);
        validarEstadoFecha(cita);
        return citaRepository.save(cita);
    }

    public List<Cita> listar() {
        return citaRepository.findAll();
    }

    public Optional<Cita> obtener(Long id) {
        return citaRepository.findById(id);
    }

    @Transactional
    public Optional<Cita> actualizar(Long id, Cita c) {
        return citaRepository.findById(id).map(db -> {
            if (c.getFechaHora()!=null) db.setFechaHora(c.getFechaHora());
            if (c.getMotivo()!=null) db.setMotivo(c.getMotivo());
            if (c.getEstado()!=null) {
                validarTransicion(db.getEstado(), c.getEstado());
                db.setEstado(c.getEstado());
            }
            if (c.getMascota()!=null && c.getMascota().getId()!=null &&
                    mascotaRepository.existsById(c.getMascota().getId())) {
                db.setMascota(c.getMascota());
            }
            if (c.getVeterinario()!=null && c.getVeterinario().getId()!=null &&
                    veterinarioRepository.existsById(c.getVeterinario().getId())) {
                db.setVeterinario(c.getVeterinario());
            }
            validarEstadoFecha(db);
            return citaRepository.save(db);
        });
    }

    @Transactional
    public boolean eliminar(Long id) {
        if (!citaRepository.existsById(id)) return false;
        citaRepository.deleteById(id);
        return true;
    }

    // Consultas nativas requeridas
    public List<Cita> pendientesPorVet(Long idVet) {
        return citaRepository.pendientesPorVet(idVet);
    }

    public List<Map<String,Object>> historialPorMascota(Long idMascota) {
        return citaRepository.historialPorMascota(idMascota);
    }

    // Reglas de negocio
    private void validarFKs(Cita c) {
        if (c.getMascota()==null || c.getMascota().getId()==null ||
                !mascotaRepository.existsById(c.getMascota().getId())) {
            throw new IllegalArgumentException("Mascota no existe");
        }
        if (c.getVeterinario()==null || c.getVeterinario().getId()==null ||
                !veterinarioRepository.existsById(c.getVeterinario().getId())) {
            throw new IllegalArgumentException("Veterinario no existe");
        }
    }

    private void validarEstadoFecha(Cita c) {
        if (c.getEstado()==Cita.Estado.Programada &&
                c.getFechaHora()!=null && c.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede programar una cita en el pasado");
        }
    }

    private void validarTransicion(Cita.Estado actual, Cita.Estado nuevo) {
        if (actual==null || nuevo==null) return;
        if (actual==Cita.Estado.Programada &&
                (nuevo==Cita.Estado.Atendida || nuevo==Cita.Estado.Cancelada)) return;
        if (actual==nuevo) return;
        throw new IllegalArgumentException("Transición de estado no permitida: "+actual+" -> "+nuevo);
    }
}

