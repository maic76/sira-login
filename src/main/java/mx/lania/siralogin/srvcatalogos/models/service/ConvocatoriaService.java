package mx.lania.siralogin.srvcatalogos.models.service;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.dao.IConvocatoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConvocatoriaService  implements IConvocatoriaService{

    @Autowired
    private IConvocatoriaRepository convocatoriaRepository;

    @Override
    public List<Convocatoria> findAll() {
        return convocatoriaRepository.findAll();
    }

    @Override
    public Convocatoria findById(Long id) {
        return convocatoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Convocatoria save(Convocatoria convocatoria) {
        return convocatoriaRepository.save(convocatoria);
    }

    @Override
    public void delete(Long id) {
        convocatoriaRepository.deleteById(id);
    }
}
