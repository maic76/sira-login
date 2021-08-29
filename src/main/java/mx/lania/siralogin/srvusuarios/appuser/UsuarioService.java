package mx.lania.siralogin.srvusuarios.appuser;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationToken;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationTokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG ="el usuario con el email %s no se encontró";
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

        confirmationTokenService.saveConfirmationToken(confirmationToken);  //genera y guarda el token de confirmación

        // TODO: ENVIAR EMAIL

        return token;
    }

    public void signUpUserEmpleado(Usuario usuario){
        boolean userExists = usuarioRepository.findByEmail(usuario.getEmail())
                .isPresent();
        if(userExists){
            throw new IllegalStateException("email ya existe");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());

        usuario.setPassword(encodedPassword);

        usuarioRepository.save(usuario);

        usuarioRepository.enableUsuario(usuario.getEmail());

    }

    public void updateUserEmpleado(Usuario usuario){
        Usuario user = usuarioRepository.findByEmail(usuario.getEmail()).orElse(null);

        if(user.equals(null)){
            throw new IllegalStateException("usuario no existe");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(usuario.getPassword());

        user.setPassword(encodedPassword);
        user.setEmail(usuario.getEmail());
        user.setUsuarioRol(usuario.getUsuarioRol());

        usuarioRepository.save(user);

        usuarioRepository.enableUsuario(user.getEmail());

    }

    public int enableUsuario(String email) {
        return usuarioRepository.enableUsuario(email);
    }

    public Usuario getUsuarioByEmail(String email){
       return usuarioRepository.findByEmail(email).orElse(null);
    }

    public List<Usuario> buscarUsuariosLania(){
        List<UsuarioRol> roles = new ArrayList<>();
        roles.add(UsuarioRol.ADMIN);
        roles.add(UsuarioRol.SEGUIMIENTO);
        return usuarioRepository.findByUsuarioRolIn(roles);
    }

    public int unableUsuario(String email) {
        return usuarioRepository.unableUsuario(email);
    }
}
