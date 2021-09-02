package mx.lania.siralogin.srvusuarios.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioRol;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioService;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.service.IAspiranteService;
import mx.lania.siralogin.srvnotificaciones.service.email.EmailSender;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import mx.lania.siralogin.srvusuarios.empleado.models.service.IEmpleadoService;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationToken;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UsuarioService usuarioService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private  final IAspiranteService iAspiranteService;
    private final IEmpleadoService iEmpleadoService;

    public String register(RegistrationRequest request) {  //acá iría el tipo de rol cuando se agreguen Admin y Seguimiento


        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail
        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }

        String token = usuarioService.signUpUser(
                new Usuario(
                        request.getEmail(),
                        request.getPassword(),
                        UsuarioRol.ASPIRANTE
                )
        );

        Aspirante aspirante = new Aspirante();
        aspirante.setApellido(request.getApellido());
        aspirante.setNombre(request.getNombre());
        aspirante.setEscuela(request.getEscuela());
        aspirante.setNoWhatsapp(request.getNoWhatsapp());

        Usuario usuarioCreado = usuarioService.getUsuarioByEmail(request.getEmail());
        if(usuarioCreado!= null){
            aspirante.setUsuario(usuarioCreado);
            iAspiranteService.save(aspirante);
        }

        String link = "http://localhost:8081/sira/usuarios/confirm?token="+token;  //generamos link de confirmación

        emailSender.send(request.getEmail(),emailSender.buildEmailRegistration(request.getNombre(),link),"Confirma tu correo"); //utilizamos el servicio de notificaciones
        return token;
    }

    public Usuario registerEmpleado(RegistrationRequestEmpleado request){
        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail
        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }
        UsuarioRol rol;
        if(request.getRol().equals("SEGUIMIENTO")){
            rol = UsuarioRol.SEGUIMIENTO;
        }else{
            rol = UsuarioRol.ADMIN;
        }

       usuarioService.signUpUserEmpleado(
                new Usuario(
                        request.getEmail(),
                        request.getPassword(),
                         rol
                )
        );

        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setClave(request.getClave());

        Usuario usuarioCreado = usuarioService.getUsuarioByEmail(request.getEmail());
        if(usuarioCreado!= null){
            empleado.setUsuario(usuarioCreado);
            iEmpleadoService.save(empleado);
        }

        return usuarioCreado;
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



    public List<Usuario> obtenerUsuariosLania(){
       return usuarioService.buscarUsuariosLania();
    }

    public Usuario editarEmpleado(RegistrationRequestEmpleado request){

        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail

        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }
        UsuarioRol rol;
        if(request.getRol().equals("SEGUIMIENTO")){
            rol = UsuarioRol.SEGUIMIENTO;
        }else{
            rol = UsuarioRol.ADMIN;
        }

        usuarioService.updateUserEmpleado(
                new Usuario(
                        request.getEmail(),
                        request.getPassword(),
                        rol
                )
        );

        Usuario usuarioEditado = usuarioService.getUsuarioByEmail(request.getEmail());

        Empleado empleado = usuarioEditado.getEmpleado();
        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setClave(request.getClave());


        if(usuarioEditado!= null){
            empleado.setUsuario(usuarioEditado);
            iEmpleadoService.save(empleado);
        }

        return usuarioEditado;
    }

    public void unableUsuario(String email){
        usuarioService.unableUsuario(email);
    }

    public void enableUsuario(String email){
        usuarioService.enableUsuario(email);
    }
}
