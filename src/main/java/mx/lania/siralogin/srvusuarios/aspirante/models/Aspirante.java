package mx.lania.siralogin.srvusuarios.aspirante.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mx.lania.siralogin.srvparticipaciones.models.Participacion;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
//@NoArgsConstructor
@Entity(name = "aspirantes")
public class Aspirante implements Serializable {
    @SequenceGenerator(
            name ="aspirantes_sequence",
            sequenceName = "aspirantes_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "aspirantes_sequence"
    )
    private Long id;
    private String nombre;
    private String apellido;
    private String escuela;
    private String noWhatsapp;
    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "aspirante", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Participacion> participaciones = new ArrayList<>();

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEscuela() {
        return escuela;
    }

    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }

    public String getNoWhatsapp() {
        return noWhatsapp;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setNoWhatsapp(String noWhatsapp) {
        this.noWhatsapp = noWhatsapp;
    }

    public void addParticipacion(Participacion participacion){
        this.participaciones.add(participacion);
    }

    public List<Participacion> getParticipaciones() {
        return participaciones;
    }

    public void setParticipaciones(List<Participacion> participaciones) {
        this.participaciones = participaciones;
    }
}
