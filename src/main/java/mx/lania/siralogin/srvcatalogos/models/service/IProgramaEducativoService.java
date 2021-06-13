package mx.lania.siralogin.srvcatalogos.models.service;

import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;

import java.util.List;
import java.util.Optional;

public interface IProgramaEducativoService {

    public List<ProgramaEducativo> findAll();

    public ProgramaEducativo findById(Long id);

    public ProgramaEducativo save(ProgramaEducativo programaEducativo);

    public void delete(Long id);

}
