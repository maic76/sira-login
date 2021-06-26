package mx.lania.siralogin.srvcatalogos.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@Entity(name = "requisitos")
public class Requisito implements Serializable {
    @SequenceGenerator(
            name ="requisitos_sequence",
            sequenceName = "requisitos_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "requisitos_sequence"
    )
    private Long id;
    private String nombre;
    private String tipo;
    private boolean esDocumento;
    private String descripcion;
    private boolean esCambiante;

    @ManyToMany(cascade ={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="requisitos_convocatorias",joinColumns = @JoinColumn(name="requisito_id"),
            inverseJoinColumns = @JoinColumn(name="convocatoria_id"))
    private List<Convocatoria> convocatorias = new ArrayList<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEsDocumento() {
        return esDocumento;
    }

    public void setEsDocumento(boolean esDocumento) {
        this.esDocumento = esDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEsCambiante() {
        return esCambiante;
    }

    public void setEsCambiante(boolean esCambiante) {
        this.esCambiante = esCambiante;
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
}
