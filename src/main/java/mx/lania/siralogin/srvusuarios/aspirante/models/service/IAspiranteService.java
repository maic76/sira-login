package mx.lania.siralogin.srvusuarios.aspirante.models.service;

import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;

import java.util.List;

public interface IAspiranteService {
    public List<Aspirante> findAll();

    public Aspirante findById(Long id);

    public Aspirante save(Aspirante aspirante);

    public void delete(Long id);
}
