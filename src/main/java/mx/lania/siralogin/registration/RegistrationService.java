package mx.lania.siralogin.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.appuser.Usuario;
import mx.lania.siralogin.appuser.UsuarioRol;
import mx.lania.siralogin.appuser.UsuarioService;
import mx.lania.siralogin.email.EmailSender;
import mx.lania.siralogin.registration.token.ConfirmationToken;
import mx.lania.siralogin.registration.token.ConfirmationTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsuarioService usuarioService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request) {  //acá iría el tipo de rol cuando se agreguen Admin y Seguimiento

        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail
        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }

        String token = usuarioService.signUpUser(
                new Usuario(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UsuarioRol.USER
                )
        );

        //emailSender.send(request.getEmail(),buildEmail(request.getFirstName()));
        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
       usuarioService.enableUsuario(
                confirmationToken.getUsuario().getEmail());
        return "confirmed";
    }
}
