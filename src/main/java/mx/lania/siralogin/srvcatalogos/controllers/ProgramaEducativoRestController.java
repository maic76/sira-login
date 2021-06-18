package mx.lania.siralogin.srvcatalogos.controllers;

import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.service.IProgramaEducativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramaEducativoRestController {

    @Autowired
    private IProgramaEducativoService programaEducativoService;

    @GetMapping("/peducativos")
    public List<ProgramaEducativo> index(){
       return programaEducativoService.findAll();
    }

    @GetMapping("/peducativos/{id}")
    public ProgramaEducativo show(@PathVariable Long id ){
        return programaEducativoService.findById(id);
    }

    @PostMapping("/peducativos")
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramaEducativo save(@RequestBody ProgramaEducativo programaEducativo){
        programaEducativo.setCreatedAt(new Date());
        return programaEducativoService.save(programaEducativo);
    }

    @PutMapping("/peducativos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramaEducativo update(@RequestBody ProgramaEducativo programaEducativo, @PathVariable Long id){

        ProgramaEducativo programaEducativoActual  = programaEducativoService.findById(id);
        programaEducativoActual.setNombre(programaEducativo.getNombre());
        programaEducativoActual.setClave(programaEducativo.getClave());
        programaEducativoActual.setUpdatedAt(new Date());
        programaEducativoActual.setDescripcion(programaEducativo.getDescripcion());
        programaEducativoActual.setVigencia(programaEducativo.getVigencia());
        programaEducativoActual.setAbreviatura(programaEducativo.getAbreviatura());
       return  programaEducativoService.save(programaEducativoActual);

    }

    @DeleteMapping("/peducativos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id ){
        programaEducativoService.delete(id);
    }
}
