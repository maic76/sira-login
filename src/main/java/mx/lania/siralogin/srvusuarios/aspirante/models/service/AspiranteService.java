package mx.lania.siralogin.srvusuarios.aspirante.models.service;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.dao.IAspiranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AspiranteService implements IAspiranteService{

    @Autowired
    private final IAspiranteRepository aspiranteRepository;

    @Override
    public List<Aspirante> findAll() {
        return aspiranteRepository.findAll();
    }

    @Override
    public Aspirante findById(Long id) {
        return aspiranteRepository.findById(id).orElse(null);
    }

    @Override
    public Aspirante save(Aspirante aspirante) {
        return aspiranteRepository.save(aspirante);
    }

    @Override
    public void delete(Long id) {
        aspiranteRepository.deleteById(id);
    }
}
