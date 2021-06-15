package mx.lania.siralogin.srvcatalogos.models.service;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;

import java.util.List;

public interface IConvocatoriaService {
    public List<Convocatoria> findAll();

    public Convocatoria findById(Long id);

    public Convocatoria save(Convocatoria convocatoria);

    public void delete(Long id);
}
