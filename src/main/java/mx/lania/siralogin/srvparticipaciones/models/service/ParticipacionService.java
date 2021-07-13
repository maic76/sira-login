package mx.lania.siralogin.srvparticipaciones.models.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.dao.IParticipacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParticipacionService implements IParticipacionService{

    @Autowired
    private final IParticipacionRepository iParticipacionRepository;

    @Override
    public List<Participacion> findAll() {
        return iParticipacionRepository.findAll();
    }

    @Override
    public Participacion findById(Long id) {
        return iParticipacionRepository.findById(id).orElse(null);
    }

    @Override
    public Participacion save(Participacion participacion) {
        return iParticipacionRepository.save(participacion);
    }

    @Override
    public void delete(Long id) {
        iParticipacionRepository.deleteById(id);
    }
}
