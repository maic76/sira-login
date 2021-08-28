package mx.lania.siralogin.srvusuarios.empleado.models.dao;

import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado,Long> {
}
