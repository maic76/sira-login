package mx.lania.siralogin.srvparticipaciones.models.service;


import mx.lania.siralogin.srvparticipaciones.models.Participacion;

import java.util.List;
import java.util.Map;

public interface IParticipacionService {
    public List<Participacion> findAll();

    public Participacion findById(Long id);

    public Participacion save(Participacion participacion);

    public void delete(Long id);

    public Map<String,Integer> calcularEntregados(Participacion participacion);
}
