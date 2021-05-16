package mx.lania.siralogin.appuser;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.registration.token.ConfirmationToken;
import mx.lania.siralogin.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "el usuario con el email %s no se encontró";
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    public String signUpUser(Usuario usuario){
       boolean userExists = usuarioRepository.findByEmail(usuario.getEmail())
                .isPresent();
       if(userExists){
           throw new IllegalStateException("email ya existe");
       }

       String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());

       usuario.setPassword(encodedPassword);

       usuarioRepository.save(usuario);

       String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
               token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                usuario
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: ENVIAR EMAIL

        return "it works";
    }

    public int enableUsuario(String email) {
        return usuarioRepository.enableUsuario(email);
    }
}
