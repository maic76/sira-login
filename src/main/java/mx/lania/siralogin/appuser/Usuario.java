package mx.lania.siralogin.appuser;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UsuarioRol usuarioRol;
    private String escuela;
    private String noWhatsapp;
    private Boolean locked = false;
    private Boolean enabled = false;

    public Usuario(String nombre,
                   String apellido,
                   String email,
                   String password,
                   UsuarioRol usuarioRol,
                   String escuela,
                   String noWhatsapp) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.usuarioRol = usuarioRol;
        this.escuela = escuela;
        this.noWhatsapp = noWhatsapp;
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

    public void setNoWhatsapp(String noWhatsapp) {
        this.noWhatsapp = noWhatsapp;
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

    public String getNombre() {
        return nombre;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getApellido() {
        return apellido;
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
