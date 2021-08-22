package mx.lania.siralogin.srvparticipaciones.models;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParticipacionDTO implements Serializable {

    private Long id;
    private Aspirante aspirante;
    private Convocatoria convocatoria;
    private List<ParticipacionRequisitoConvocatoria> participacionRequisitosConvocatoria = new ArrayList<>();
    private Date fechaInscripcion;
    private boolean activa;
    private String estatus;
    private int total;
    private int entregados;

    public ParticipacionDTO(){

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getEntregados() {
        return entregados;
    }

    public void setEntregados(int entregados) {
        this.entregados = entregados;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aspirante getAspirante() {
        return aspirante;
    }

    public void setAspirante(Aspirante aspirante) {
        this.aspirante = aspirante;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

    public List<ParticipacionRequisitoConvocatoria> getParticipacionRequisitosConvocatoria() {
        return participacionRequisitosConvocatoria;
    }

    public void setParticipacionRequisitosConvocatoria(List<ParticipacionRequisitoConvocatoria> participacionRequisitosConvocatoria) {
        this.participacionRequisitosConvocatoria = participacionRequisitosConvocatoria;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
