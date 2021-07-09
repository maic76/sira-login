package mx.lania.siralogin.srvusuarios.aspirante.models.dao;

import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAspiranteRepository extends JpaRepository<Aspirante,Long> {
}
