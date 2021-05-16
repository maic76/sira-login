package mx.lania.siralogin.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "el usuario con el email %s no se encontró";
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

       //TODO: Send confirmation token

        return "it works";
    }
}
