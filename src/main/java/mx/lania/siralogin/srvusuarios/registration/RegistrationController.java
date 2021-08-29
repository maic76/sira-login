package mx.lania.siralogin.srvusuarios.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioRepository;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
/*@CrossOrigin(origins = "http://localhost:8080")*/
@RequestMapping(path = "sira/usuarios")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UsuarioRepository usuarioRepository;


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

    @PutMapping(path="/empleados")
    public Usuario editaUsuarioLania(@RequestBody RegistrationRequestEmpleado requestEmpleado){
        return registrationService.editarEmpleado(requestEmpleado);

    }

    @DeleteMapping(path="/empleados")
    public ResponseEntity <?> deshabilitaUsuario(@RequestBody RegistrationRequestEmpleado requestEmpleado){
        Usuario user = usuarioRepository.findByEmail(requestEmpleado.getEmail()).orElse(null);

        Map<String,Object> response = new HashMap<>();
        String mensaje = "";
        if(user.equals(null)){
            mensaje = "usuario no existe";
            throw new IllegalStateException("usuario no existe");
        }
             registrationService.unableUsuario(requestEmpleado.getEmail());
            mensaje = "usuario deshabilitado con éxito";
         response.put("mensaje",mensaje);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path="/empleados")
    public ResponseEntity <?> habilitaUsuarioEmpleado(@RequestBody RegistrationRequestEmpleado requestEmpleado){
        Usuario user = usuarioRepository.findByEmail(requestEmpleado.getEmail()).orElse(null);

        Map<String,Object> response = new HashMap<>();
        String mensaje = "";
        if(user.equals(null)){
            mensaje = "usuario no existe";
            throw new IllegalStateException("usuario no existe");
        }
        registrationService.enableUsuario(requestEmpleado.getEmail());
        mensaje = "usuario habilitado con éxito";
        response.put("mensaje",mensaje);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

}
