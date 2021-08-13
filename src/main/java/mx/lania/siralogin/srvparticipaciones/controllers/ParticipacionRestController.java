package mx.lania.siralogin.srvparticipaciones.controllers;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;
import mx.lania.siralogin.srvcatalogos.models.service.ConvocatoriaService;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.ParticipacionRequisitoConvocatoria;
import mx.lania.siralogin.srvparticipaciones.models.service.ParticipacionService;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;
import mx.lania.siralogin.srvusuarios.appuser.UsuarioService;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.service.AspiranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionRestController {

    @Autowired
    private AspiranteService aspiranteService;
    @Autowired
    private ConvocatoriaService convocatoriaService;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")  //obtiene participaciones de un aspirante
    public List<Participacion> findParticipaciones(){

        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioService.getUsuarioByEmail(userName);
        Aspirante aspirante = usuario.getAspirante();

        return aspirante.getParticipaciones();
    }

    @PostMapping("/convocatorias/{idConvocatoria}")
    public ResponseEntity<?> crearParticipacion(/*@PathVariable Long idAspirante, */ @PathVariable Long idConvocatoria){

        Map<String,Object> response = new HashMap<>();
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioService.getUsuarioByEmail(userName);
        Aspirante aspirante = usuario.getAspirante();

        try{
            Convocatoria convocatoria = convocatoriaService.findById(idConvocatoria);
            Participacion participacion = new Participacion();
            participacion.setConvocatoria(convocatoria);
            participacion.setAspirante(aspirante);
            participacion.setActiva(true);
            participacion.setFechaInscripcion(new Date());
            for (RequisitoConvocatoria rc : convocatoria.getRequisitoConvocatorias()) {
                ParticipacionRequisitoConvocatoria prc = new ParticipacionRequisitoConvocatoria();
                prc.setRequisitoConvocatoria(rc);
                prc.setParticipacion(participacion);
                prc.setEntregado(false);
                prc.setRutaArchivo(null);
                participacion.addParticipacionRequisitosConvocatoria(prc);
            }

            aspirante.addParticipacion(participacion);
            aspiranteService.save(aspirante);
            // TODO FALTA CREAR LA PARTICIPACION_REQUISITO_CONVOCATORIA POR CADA REQ_CONVOCATORIA DE LA CONV
        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar update en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //response.put("mensaje", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        // TODO obtuve el usuario, falta implementar para que busque su id de aspirante :)
        response.put("mensaje","Se registró su participación con éxito!");
        response.put("participaciones",aspirante.getParticipaciones());
        return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }


}
