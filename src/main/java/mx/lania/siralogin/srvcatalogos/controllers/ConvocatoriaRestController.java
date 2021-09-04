package mx.lania.siralogin.srvcatalogos.controllers;

import com.sun.mail.iap.Response;
import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.Requisito;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;
import mx.lania.siralogin.srvcatalogos.models.service.IConvocatoriaService;
import mx.lania.siralogin.srvcatalogos.models.service.IProgramaEducativoService;
import mx.lania.siralogin.srvcatalogos.models.service.IRequisitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sira/catalogos")
public class ConvocatoriaRestController {

    @Autowired
    IConvocatoriaService convocatoriaService;
    @Autowired
    IProgramaEducativoService programaEducativoService;
    @Autowired
    IRequisitoService requisitoService;

    @GetMapping("/convocatorias")
    public ResponseEntity<?>  index(){
        Map<String,Object> response = new HashMap<>();
        List<Convocatoria> convocatorias = convocatoriaService.findAll();
        int numConv = convocatorias.size();
        response.put("mensaje","Exito al consultar las convocatorias");
        response.put("convocatorias",convocatorias);
        response.put("numConv",numConv);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);

    }

    @PostMapping("/convocatorias/peducativos/{idProgEducativo}")
    @ResponseStatus(HttpStatus.CREATED)
    public Convocatoria save(@RequestBody Convocatoria convocatoria,@PathVariable Long idProgEducativo){
        ProgramaEducativo progEducativo = programaEducativoService.findById(idProgEducativo);
        convocatoria.setCreatedAt(new Date());
        convocatoria.setProgramaEducativo(progEducativo);
        return convocatoriaService.save(convocatoria);
    }

    @PutMapping("/convocatorias/{id}/peducativos/{idProgEducativo}")
    @ResponseStatus(HttpStatus.CREATED)
    public Convocatoria update(@RequestBody Convocatoria convocatoria, @PathVariable Long id, @PathVariable Long idProgEducativo){
       Convocatoria convocatoriaActual = convocatoriaService.findById(id);
        ProgramaEducativo progEducativo = programaEducativoService.findById(idProgEducativo);
       convocatoriaActual.setProgramaEducativo(progEducativo);
       convocatoriaActual.setDescripcion(convocatoria.getDescripcion());
       convocatoriaActual.setNombre(convocatoria.getNombre());
       convocatoriaActual.setFechaInicio(convocatoria.getFechaInicio());
       convocatoriaActual.setFechaTermino(convocatoria.getFechaTermino());
       convocatoriaActual.setCantAspirantes(convocatoria.getCantAspirantes());
       convocatoriaActual.setUpdatedAt(new Date());
       return convocatoriaService.save(convocatoriaActual);
    }

    @DeleteMapping("/convocatorias/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        convocatoriaService.delete(id);
    }

    @PostMapping("/convocatorias/{idConvocatoria}/requisitos/{idRequisito}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveConvocatoria(@PathVariable Long idConvocatoria, @PathVariable Long idRequisito, @RequestParam int cantidad,
                                              @RequestParam String original, @RequestParam boolean indispensable){

        Convocatoria convocatoriaActualizada = null;
        Map<String,Object> response = new HashMap<>();
        try{
            Requisito requisito = requisitoService.findById(idRequisito);
            Convocatoria convocatoria = convocatoriaService.findById(idConvocatoria);
            RequisitoConvocatoria requisitoConvocatoria = new RequisitoConvocatoria();
            requisitoConvocatoria.setRequisito(requisito);
            requisitoConvocatoria.setCantidad(cantidad);
            requisitoConvocatoria.setIndispensable(indispensable);
            requisitoConvocatoria.setOriginal(original);
            requisitoConvocatoria.setConvocatoria(convocatoria);
            convocatoria.addRequisitoConvocatorias(requisitoConvocatoria);
           convocatoriaActualizada =  convocatoriaService.save(convocatoria);

        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar update en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Exito al agregar Requisito a la Convocatoria!");
        response.put("requisitos",convocatoriaActualizada.getRequisitoConvocatorias());
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @GetMapping("/convocatorias/{idConvocatoria}/requisitos")
    @ResponseStatus(HttpStatus.OK)
    public  List<RequisitoConvocatoria> requisitosConvocatoria(@PathVariable Long idConvocatoria){
        Convocatoria convocatoria = convocatoriaService.findById(idConvocatoria);
        return convocatoria.getRequisitoConvocatorias();
    }

    @DeleteMapping("/convocatorias/{idConvocatoria}/requisitos/{idRequisitos}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> removeRequisitoConvocatoria(@PathVariable Long idConvocatoria, @PathVariable Long idRequisitos, @RequestParam int cantidad,
                                                         @RequestParam String original, @RequestParam boolean indispensable){
        Convocatoria convocatoriaActualizada = null;
        Map<String,Object> response = new HashMap<>();
        try{
            Requisito requisito = requisitoService.findById(idRequisitos);
            Convocatoria convocatoria = convocatoriaService.findById(idConvocatoria);
            RequisitoConvocatoria requisitoConvocatoria = new RequisitoConvocatoria();
            requisitoConvocatoria.setRequisito(requisito);
            requisitoConvocatoria.setCantidad(cantidad);
            requisitoConvocatoria.setIndispensable(indispensable);
            requisitoConvocatoria.setOriginal(original);
            requisitoConvocatoria.setConvocatoria(convocatoria);
            convocatoria.removeRequisitoConvocatoria(requisitoConvocatoria);
            convocatoriaActualizada =  convocatoriaService.save(convocatoria);

        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar eliminado en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Exito al eliminar Requisito a la Convocatoria!");
        response.put("requisitos",convocatoriaActualizada.getRequisitoConvocatorias());
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @GetMapping("/convocatorias/{idConvocatoria}/participaciones")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> participacionesConvocatoria(@PathVariable Long idConvocatoria){
        Map<String,Object> response = new HashMap<>();
        Convocatoria convocatoria = null;
        try {
            convocatoria = convocatoriaService.findById(idConvocatoria);
            convocatoria.getParticipaciones();
        }catch (DataAccessException ex){
            response.put("mensaje","Error al realizar la consulta a la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Exito al eliminar Requisito a la Convocatoria!");
        response.put("convocatoria",convocatoria);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

}
