package mx.lania.siralogin.srvusuarios.empleado.models.service;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import mx.lania.siralogin.srvusuarios.empleado.models.dao.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpleadoService implements IEmpleadoService{
    @Autowired
    private final IEmpleadoRepository iEmpleadoRepository;

    @Override
    public List<Empleado> findAll() {
        return iEmpleadoRepository.findAll();
    }

    @Override
    public Empleado findById(Long id) {
        return iEmpleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Empleado save(Empleado empleado) {
        return iEmpleadoRepository.save(empleado);
    }

    @Override
    public void delete(Long id) {
            iEmpleadoRepository.deleteById(id);
    }
}
