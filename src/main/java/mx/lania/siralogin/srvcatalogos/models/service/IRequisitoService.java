package mx.lania.siralogin.srvcatalogos.models.service;

import mx.lania.siralogin.srvcatalogos.models.Requisito;

import java.util.List;

public interface IRequisitoService {
    public List<Requisito> findAll();

    public Requisito findById(Long id);

    public Requisito save(Requisito requisito);

    public void delete(Long id);
}
