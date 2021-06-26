package mx.lania.siralogin.srvcatalogos.models.service;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvcatalogos.models.Requisito;
import mx.lania.siralogin.srvcatalogos.models.dao.IRequisitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RequisitoService implements IRequisitoService{

    @Autowired
    private final IRequisitoRepository iRequisitoRepository;

    @Override
    public List<Requisito> findAll() {
        return null;
    }

    @Override
    public Requisito findById(Long id) {
        return null;
    }

    @Override
    public Requisito save(Requisito requisito) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
