package mx.lania.siralogin.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
}
