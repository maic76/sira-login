package mx.lania.siralogin.srvparticipaciones.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mx.lania.siralogin.srvcatalogos.models.RequisitoConvocatoria;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="participaciones_requisitos_convocatorias")
public class ParticipacionRequisitoConvocatoria implements Serializable {
    @SequenceGenerator(
            name ="part_req_conv_sequence",
            sequenceName = "part_req_conv_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "part_req_conv_sequence"
    )
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="participacion_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Participacion participacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="requisitos_convocatorias_id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private RequisitoConvocatoria requisitoConvocatoria;

    private boolean entregado;

    private String rutaArchivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participacion getParticipacion() {
        return participacion;
    }

    public void setParticipacion(Participacion participacion) {
        this.participacion = participacion;
    }

    public RequisitoConvocatoria getRequisitoConvocatoria() {
        return requisitoConvocatoria;
    }

    public void setRequisitoConvocatoria(RequisitoConvocatoria requisitoConvocatoria) {
        this.requisitoConvocatoria = requisitoConvocatoria;
    }

    public boolean isEntregado() {
        return entregado;
    }

    public void setEntregado(boolean entregado) {
        this.entregado = entregado;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
