package mx.lania.siralogin.srvcatalogos.models;

import com.fasterxml.jackson.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity(name = "convocatorias")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Convocatoria implements Serializable {

    @SequenceGenerator(
            name ="convocatorias_sequence",
            sequenceName = "convocatorias_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "convocatorias_sequence"
    )
    private Long id;
    private String nombre;
    private String descripcion;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_termino")
    @Temporal(TemporalType.DATE)
    private Date fechaTermino;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProgramaEducativo programaEducativo;
    private int cantAspirantes;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

   /* @ManyToMany(mappedBy = "convocatorias")
    private List<Requisito> requisitos = new ArrayList<>();*/

    @OneToMany(mappedBy = "convocatoria", cascade = CascadeType.ALL)
    private List<RequisitoConvocatoria> requisitoConvocatorias = new ArrayList<>();


    public Long getId() {
        return id;
    }

    @JsonBackReference
    public List<RequisitoConvocatoria> getRequisitoConvocatorias() {
        return requisitoConvocatorias;
    }

    public void setRequisitoConvocatorias(List<RequisitoConvocatoria> requisitoConvocatorias) {
        this.requisitoConvocatorias = requisitoConvocatorias;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    //@JsonBackReference
    public ProgramaEducativo getProgramaEducativo() {
        return programaEducativo;
    }

    public void setProgramaEducativo(ProgramaEducativo programaEducativo) {
        this.programaEducativo = programaEducativo;
    }

    public int getCantAspirantes() {
        return cantAspirantes;
    }

    public void setCantAspirantes(int cantAspirantes) {
        this.cantAspirantes = cantAspirantes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void addRequisitoConvocatorias(RequisitoConvocatoria requisitoConvocatoria){
        this.requisitoConvocatorias.add(requisitoConvocatoria);
    }
}
