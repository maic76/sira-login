package mx.lania.siralogin.srvnotificaciones.service.email.controllers;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.service.IConvocatoriaService;
import mx.lania.siralogin.srvcatalogos.models.service.IProgramaEducativoService;
import mx.lania.siralogin.srvnotificaciones.service.email.EmailSender;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sira/notificaciones")
public class NotificacionesRestController {

    @Autowired
    private IConvocatoriaService convocatoriaService;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/convocatorias/{idConvocatoria}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> enviarCorreoParticipacion(@PathVariable Long idConvocatoria){

        Map<String,Object> response = new HashMap<>();
        Convocatoria convocatoria = convocatoriaService.findById(idConvocatoria);
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioService.getUsuarioByEmail(userName);
        String nombre = usuario.getAspirante().getNombre();
        nombre = nombre.contains(" ") ? nombre.split(" ")[0] : nombre;
        String correo = emailSender.buildEmailRegistroConvocatoria(nombre,convocatoria);
        String registroConv = ("Registro en Convocatoria ").concat(convocatoria.getNombre());
        emailSender.send(nombre,correo,registroConv);
        response.put("mensaje","Exito al enviar correo de participacion");
        response.put("convocatoria",convocatoria);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
