package mx.lania.siralogin.srvcatalogos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

   // @ManyToMany(cascade ={CascadeType.PERSIST, CascadeType.MERGE})
   // @JoinTable(name="requisitos_convocatorias",joinColumns = @JoinColumn(name="requisito_id"),
      //      inverseJoinColumns = @JoinColumn(name="convocatoria_id"))

    //private List<Convocatoria> convocatorias = new ArrayList<>();

    @OneToMany(mappedBy = "requisito", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private List<RequisitoConvocatoria> requisitoConvocatorias = new ArrayList<>();

    @JsonBackReference
    public List<RequisitoConvocatoria> getRequisitoConvocatorias() {
        return requisitoConvocatorias;
    }

    public void setRequisitoConvocatorias(List<RequisitoConvocatoria> requisitoConvocatorias) {
        this.requisitoConvocatorias = requisitoConvocatorias;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requisito requisito = (Requisito) o;
        return esDocumento == requisito.esDocumento && esCambiante == requisito.esCambiante && nombre.equals(requisito.nombre) && Objects.equals(tipo, requisito.tipo) && descripcion.equals(requisito.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, esDocumento, descripcion, esCambiante);
    }
}
