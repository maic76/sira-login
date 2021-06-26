package mx.lania.siralogin.srvcatalogos.models.dao;

import mx.lania.siralogin.srvcatalogos.models.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequisitoRepository extends JpaRepository<Requisito,Long> {
}
