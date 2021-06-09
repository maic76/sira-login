package mx.lania.siralogin.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String nombre;
    private final String apellido;
    private final String email;
    private final String password;
    private final String escuela;
    private final String noWhatsapp;

}
