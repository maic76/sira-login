package mx.lania.siralogin.srvusuarios.registration;

import lombok.AllArgsConstructor;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioRepository;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioRol;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioService;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.service.IAspiranteService;
import mx.lania.siralogin.srvusuarios.email.EmailSender;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import mx.lania.siralogin.srvusuarios.empleado.models.service.IEmpleadoService;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationToken;
import mx.lania.siralogin.srvusuarios.registration.token.ConfirmationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
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

        String link = "http://localhost:8081/sira/usuarios/confirm?token="+token;
        emailSender.send(request.getEmail(),buildEmail(request.getNombre(),link));
        return token;
    }

    public Usuario registerEmpleado(RegistrationRequestEmpleado request){
        boolean isValidEmail = emailValidator.test(request.getEmail()); // validamos el mail
        if(!isValidEmail){
            throw new IllegalStateException("Email no válido");
        }
        UsuarioRol rol;
        if(request.getRol().equals("seguimiento")){
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

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirma tu correo</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hola " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Gracias por registrarte. Porfavor haz click en el link de abajo para activar tu cuenta: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activar Cuenta</a> </p></blockquote>\n El link expirará en 15 minutos. <p>Hasta pronto.</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
