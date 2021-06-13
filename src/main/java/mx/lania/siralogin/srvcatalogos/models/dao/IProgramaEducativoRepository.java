package mx.lania.siralogin.srvcatalogos.models.dao;

import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProgramaEducativoRepository extends JpaRepository<ProgramaEducativo,Long> {


}
