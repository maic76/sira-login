package mx.lania.siralogin.srvparticipaciones.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mx.lania.siralogin.srvcatalogos.models.Convocatoria;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name="participaciones")
public class Participacion implements Serializable {
    @SequenceGenerator(
            name ="participacion_sequence",
            sequenceName = "participacion_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "participacion_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="aspirante_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Aspirante aspirante;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="convocatoria_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Convocatoria convocatoria;

    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;

    private boolean activa;

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
}
