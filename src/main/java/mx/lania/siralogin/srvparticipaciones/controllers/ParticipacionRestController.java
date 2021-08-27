package mx.lania.siralogin.srvparticipaciones.controllers;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;
import mx.lania.siralogin.srvcatalogos.models.service.ConvocatoriaService;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvparticipaciones.models.ParticipacionDTO;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/participaciones")
public class ParticipacionRestController {

    @Autowired
    private AspiranteService aspiranteService;
    @Autowired
    private ConvocatoriaService convocatoriaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ParticipacionService participacionService;

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
            participacion.setEstatus("subir requisitos"); // subir requisitos o en validación
            participacion.setFechaInscripcion(new Date());
            for (RequisitoConvocatoria rc : convocatoria.getRequisitoConvocatorias()) {
                ParticipacionRequisitoConvocatoria prc = new ParticipacionRequisitoConvocatoria();
                prc.setRequisitoConvocatoria(rc);
                prc.setParticipacion(participacion);
                prc.setEntregado(false);
                prc.setValidado(false);
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

    @PutMapping("/{idParticipacion}/prc/{idPrc}")
    public ResponseEntity<?> subirDocumento(@PathVariable Long idParticipacion, @PathVariable Long idPrc, @RequestParam("file") MultipartFile file){
        Map<String,Object> response = new HashMap<>();
        Participacion participacion = null;
        Map<String,Integer> entregados = new HashMap<>();

                try{
                    if(file == null || file.isEmpty()){
                        response.put("mensaje", "Por favor seleccione un archivo");
                        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
                    }
                       /* StringBuilder builder = new StringBuilder();
                    builder.append(System.getProperty("user.home"));
                    builder.append(File.separator);
                    builder.append("spring_upload_example");*/
                  /*  builder.append(File.separator);
                    builder.append(file.getOriginalFilename());
                     Path path = Paths.get(builder.toString());*/

                    byte[] fileBytes = file.getBytes();
                   String nombreRandom =  UUID.randomUUID().toString().concat(".pdf");
                     //String directorioPDF = ".//src//main//resources//static//documentos//";
                    String directorioPDF = "C://siratemp//uploads";
                     //Path directorioPDF = Paths.get("src//main//resources//static//images");
                   // String rutaAbsoluta = directorioPDF.toFile().getAbsolutePath();
                    //Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ file.getOriginalFilename());
                    Path rutaCompleta = Paths.get(directorioPDF + "//"+ nombreRandom );
                    Files.write(rutaCompleta, fileBytes);

                    participacion = participacionService.findById(idParticipacion);
                    for (ParticipacionRequisitoConvocatoria prc : participacion.getParticipacionRequisitosConvocatoria()) {
                        if (prc.getId() == idPrc) {
                            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                    .path("/uploads/")
                                    .path(nombreRandom)
                                    .toUriString();

                            prc.setEntregado(true);
                            prc.setRutaArchivo(fileDownloadUri);
                        }
                    }

                    entregados = participacionService.calcularEntregados(participacion);
                    if(entregados.get("entregados").equals(entregados.get("total"))){
                        participacion.setEstatus("en validación");
                    }

                    participacionService.save(participacion);


                    response.put("mensaje","Se subió el archivo con éxito!");
                    response.put("participacion", participacion);
                    response.put("entregados", entregados.get("entregados"));
                    response.put("total", entregados.get("total"));


                }catch (IOException ex){
                    response.put("mensaje", "Error al manejar el archivo");
                    response.put("error",ex.getMessage());
                }
        return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{idParticipacion}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getParticipacion(@PathVariable Long idParticipacion){

      /*  String userName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioService.getUsuarioByEmail(userName);
        Aspirante aspirante = usuario.getAspirante();*/

        Map<String,Object> response = new HashMap<>();
        Participacion participacion = null;
        Map<String,Integer> ent = new HashMap<>();

        try{
            participacion = participacionService.findById(idParticipacion);
            ent = participacionService.calcularEntregados(participacion);

        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar consulta en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Se obtuvo la participación con exito");
        response.put("participacion",participacion);
        response.put("total", ent.get("total"));
        response.put("entregados",ent.get("entregados"));
        return  new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);

    }





}
