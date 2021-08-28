package mx.lania.siralogin.srvusuarios.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequestEmpleado {

    private final String nombre;
    private final String apellido;
    private final String email;
    private final String password;
    private final String clave;
    private final String rol;

}
