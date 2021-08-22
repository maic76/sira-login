package mx.lania.siralogin.srvparticipaciones.models.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.ParticipacionRequisitoConvocatoria;
import mx.lania.siralogin.srvparticipaciones.models.dao.IParticipacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String,Integer> calcularEntregados(Participacion participacion){
        int entregados =0;
        int total = 0;
        Map<String,Integer> result = new HashMap<>();
        for (ParticipacionRequisitoConvocatoria prc : participacion.getParticipacionRequisitosConvocatoria()) {
            if(prc.isEntregado()){
                entregados++;
            }
            total++;
        }
        result.put("entregados",entregados);
        result.put("total",total);
        return result;
    }
}
