package mx.lania.siralogin.srvcatalogos.models.dao;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IConvocatoriaRepository extends JpaRepository<Convocatoria,Long> {
}
