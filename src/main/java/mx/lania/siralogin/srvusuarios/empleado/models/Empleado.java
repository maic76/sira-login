package mx.lania.siralogin.srvusuarios.empleado.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mx.lania.siralogin.srvusuarios.appuser.Usuario;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity(name="empleados")
public class Empleado {
    @SequenceGenerator(
            name ="empleados_sequence",
            sequenceName = "empleados_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "empleados_sequence"
    )
    private Long id;
    private String nombre;
    private String apellido;
    private String clave;
    @OneToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
