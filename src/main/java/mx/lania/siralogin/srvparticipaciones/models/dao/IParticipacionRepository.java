package mx.lania.siralogin.srvparticipaciones.models.dao;

import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParticipacionRepository  extends JpaRepository<Participacion,Long> {

}
