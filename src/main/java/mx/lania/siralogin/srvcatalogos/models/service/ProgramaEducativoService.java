package mx.lania.siralogin.srvcatalogos.models.service;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.dao.IProgramaEducativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgramaEducativoService implements IProgramaEducativoService {

    @Autowired
    private final IProgramaEducativoRepository programaEducativoRepository;


    @Override
    public List<ProgramaEducativo> findAll() {
        return programaEducativoRepository.findAll();
    }

    @Override
    public ProgramaEducativo findById(Long id) {
        return programaEducativoRepository.findById(id).orElse(null);
    }

    @Override
    public ProgramaEducativo save(ProgramaEducativo programaEducativo) {
        return programaEducativoRepository.save(programaEducativo);
    }

    @Override
    public void delete(Long id) {
         programaEducativoRepository.deleteById(id);
    }

}
