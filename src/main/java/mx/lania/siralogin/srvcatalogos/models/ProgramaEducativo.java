package mx.lania.siralogin.srvcatalogos.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
//@NoArgsConstructor
@Entity (name = "prog_educativos")
public class ProgramaEducativo implements Serializable {

    @SequenceGenerator(
            name ="prog_educativos_sequence",
            sequenceName = "prog_educativos_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "prog_educativos_sequence"
    )
    private Long id;
    private String nombre;
    private String clave;
    private String descripcion;
    private Date vigencia;
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.DATE)
    private Date deletedAt;
    private String abreviatura;

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    @OneToMany(mappedBy = "programaEducativo",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Convocatoria> convocatorias;

    public ProgramaEducativo() {
        convocatorias = new ArrayList<Convocatoria>();
    }

    public Long getId() {
        return id;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
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

    @JsonManagedReference
    public List<Convocatoria> getConvocatorias() {
        return convocatorias;
    }

    public void setConvocatorias(List<Convocatoria> convocatorias) {
        this.convocatorias = convocatorias;
    }

    public void addConvocatoria(Convocatoria convocatoria){
        this.convocatorias.add(convocatoria);
    }
}
