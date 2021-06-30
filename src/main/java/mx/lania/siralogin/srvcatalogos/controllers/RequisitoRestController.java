package mx.lania.siralogin.srvcatalogos.controllers;

import mx.lania.siralogin.srvcatalogos.models.Requisito;
import mx.lania.siralogin.srvcatalogos.models.service.RequisitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RequisitoRestController {

    @Autowired
    private RequisitoService requisitoService;

    @GetMapping("/requisitos")
    public List<Requisito> findAll(){
        return requisitoService.findAll();
    }

    @GetMapping("/requisitos/{id}")
    public ResponseEntity<?> findRequisito(@PathVariable Long idRequisito){
            Requisito requisito = null;
            Map<String,Object> response = new HashMap<>();
            try {
              requisito =  requisitoService.findById(idRequisito);
            }catch (DataAccessException ex){
                response.put("mensaje","Error al realizar la consluta a la BD ");
                response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
                return new  ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(requisito == null){
                response.put("mensaje","El requisito con el ID: ".concat(idRequisito.toString().concat(" no existe en la BD")));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Requisito>(requisito,HttpStatus.OK);
    }

    @PostMapping("/requisitos")
    public ResponseEntity<?> save(@RequestBody Requisito requisito){
        Requisito nuevoRequisito = null;
        Map<String,Object> response = new HashMap<>();
        try {
                nuevoRequisito = requisitoService.save(requisito);
        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar insert en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Exito al insertar Requisito!");
        response.put("requisito",nuevoRequisito);
       return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/requisitos/{id}")
    public ResponseEntity<?> updateRequisito(@RequestBody Requisito requisito, @PathVariable Long id){
        Requisito requisitoActualizado = null;
        Map<String,Object> response = new HashMap<>();
        try {
            Requisito requisitoActual = requisitoService.findById(id);
            requisitoActual.setTipo(requisito.getTipo());
            requisitoActual.setDescripcion(requisito.getDescripcion());
            requisitoActual.setNombre(requisito.getNombre());
            requisitoActual.setEsCambiante(requisito.isEsCambiante());
            requisitoActual.setEsDocumento(requisito.isEsDocumento());

            requisitoActualizado = requisitoService.save(requisitoActual);

        }catch (DataAccessException ex){
            response.put("mensaje", "Error al realizar update en la BD");
            response.put("error",ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Exito al actualizar Requisito!");
        response.put("requisito",requisitoActualizado);
        return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/requisitos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequisito(@PathVariable Long id){
            requisitoService.delete(id);
    }
}
