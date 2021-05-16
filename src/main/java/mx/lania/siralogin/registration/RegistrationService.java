package mx.lania.siralogin.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.appuser.Usuario;
import mx.lania.siralogin.appuser.UsuarioRol;
import mx.lania.siralogin.appuser.UsuarioService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsuarioService usuarioService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {

        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail
        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }

        return usuarioService.signUpUser(
                new Usuario(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UsuarioRol.USER
                )
        );
    }
}
