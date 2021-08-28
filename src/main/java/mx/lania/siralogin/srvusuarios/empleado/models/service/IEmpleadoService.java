package mx.lania.siralogin.srvusuarios.empleado.models.service;

import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;

import java.util.List;

public interface IEmpleadoService {
    public List<Empleado> findAll();

    public Empleado findById(Long id);

    public Empleado save(Empleado empleado);

    public void delete(Long id);
}
