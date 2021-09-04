package mx.lania.siralogin.srvcatalogos.controllers;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvcatalogos.models.ProgramaEducativo;
import mx.lania.siralogin.srvcatalogos.models.service.IProgramaEducativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sira/catalogos")
public class ProgramaEducativoRestController {

    @Autowired
    private IProgramaEducativoService programaEducativoService;

    @GetMapping("/peducativos")
    public ResponseEntity<?> index(){
        Map<String,Object> response = new HashMap<>();
        List<ProgramaEducativo> peducativos = programaEducativoService.findAll();
        int numPE = peducativos.size();
        response.put("mensaje","Exito al consultar los programas educativos");
        response.put("peducativos",peducativos);
        response.put("numPE",numPE);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
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
