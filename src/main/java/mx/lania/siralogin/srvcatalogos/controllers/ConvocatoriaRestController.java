package mx.lania.siralogin.srvcatalogos.controllers;

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
@RequestMapping("/api")
public class ConvocatoriaRestController {

    @Autowired
    IConvocatoriaService convocatoriaService;
    @Autowired
    IProgramaEducativoService programaEducativoService;
    @Autowired
    IRequisitoService requisitoService;

    @GetMapping("/convocatorias")
    public List<Convocatoria> index(){
        return convocatoriaService.findAll();
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
        response.put("requisito",convocatoriaActualizada);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
}
