package mx.lania.siralogin.srvusuarios.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.lania.siralogin.srvusuarios.aspirante.models.Aspirante;
import mx.lania.siralogin.srvusuarios.empleado.models.Empleado;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {



    @SequenceGenerator(
            name ="usuario_sequence",
            sequenceName = "usuario_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_sequence"
    )
    private Long id;

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UsuarioRol usuarioRol;

    private Boolean locked = false;
    private Boolean enabled = false;

    @OneToOne(mappedBy = "usuario")
    private Aspirante aspirante;

    @OneToOne(mappedBy = "usuario")
    private Empleado empleado;


    public Usuario(
                   String email,
                   String password,
                   UsuarioRol usuarioRol
                 ) {

        this.email = email;
        this.password = password;
        this.usuarioRol = usuarioRol;

    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Aspirante getAspirante() {
        return aspirante;
    }

    public void setAspirante(Aspirante aspirante) {
        this.aspirante = aspirante;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(usuarioRol.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
