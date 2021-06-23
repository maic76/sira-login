package mx.lania.siralogin.srvcatalogos.controllers;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.service.IConvocatoriaService;
import mx.lania.siralogin.srvcatalogos.models.service.IProgramaEducativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConvocatoriaRestController {

    @Autowired
    IConvocatoriaService convocatoriaService;
    @Autowired
    IProgramaEducativoService programaEducativoService;

    @GetMapping("/convocatorias")
    public List<Convocatoria> index(){
        return convocatoriaService.findAll();
    }

    @PostMapping("/convocatorias")
    @ResponseStatus(HttpStatus.CREATED)
    public Convocatoria save(@RequestBody Convocatoria convocatoria,@RequestParam Long idProgEducativo){
        ProgramaEducativo progEducativo = programaEducativoService.findById(idProgEducativo);
        convocatoria.setCreatedAt(new Date());
        convocatoria.setProgramaEducativo(progEducativo);
        return convocatoriaService.save(convocatoria);
    }

    @PutMapping("/convocatorias/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Convocatoria update(@RequestBody Convocatoria convocatoria, @PathVariable Long id, @RequestParam Long idProgEducativo){
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
}
