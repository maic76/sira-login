package mx.lania.siralogin.srvusuarios.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
/*@CrossOrigin(origins = "http://localhost:8080")*/
@RequestMapping(path = "sira/usuarios")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<Void> confirm(@RequestParam("token") String token){
        if(registrationService.confirmToken(token).contains("confirmed")){
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("http://localhost:8080/confirmado")).build();
        }
        return null;
    }

    @PostMapping(path="/empleados")
    public Usuario registerEmpleado(@RequestBody RegistrationRequestEmpleado requestEmpleado){
        return registrationService.registerEmpleado(requestEmpleado);
    }

    @GetMapping(path="/empleados")
    public List<Usuario> obtenerUsuariosLania(){
        return registrationService.obtenerUsuariosLania();
    }

}
