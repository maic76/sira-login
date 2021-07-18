package mx.lania.siralogin.srvparticipaciones.controllers;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;
import mx.lania.siralogin.srvcatalogos.models.service.ConvocatoriaService;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.ParticipacionRequisitoConvocatoria;
import mx.lania.siralogin.srvparticipaciones.models.service.ParticipacionService;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.aspirante.models.service.AspiranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ParticipacionRestController {

    @Autowired
    private AspiranteService aspiranteService;
    @Autowired
    private ConvocatoriaService convocatoriaService;

    @GetMapping("/participaciones/aspirantes/{idAspirante}")
    public List<Participacion> findParticipaciones(@PathVariable Long idAspirante){
        Aspirante aspirante = aspiranteService.findById(idAspirante);

        return aspirante.getParticipaciones();
    }

    @PostMapping("/participaciones/aspirantes/{idAspirante}/convocatorias/{idConvocatoria}")
    public ResponseEntity<?> crearParticipacion(@PathVariable Long idAspirante, @PathVariable Long idConvocatoria){

        Map<String,Object> response = new HashMap<>();
        Aspirante aspirante = aspiranteService.findById(idAspirante);

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

        response.put("mensaje","Exito al agregar Participación al Aspirante!");
        response.put("participaciones",aspirante.getParticipaciones());
        return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }


}
