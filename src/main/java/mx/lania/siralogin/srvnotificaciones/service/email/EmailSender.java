package mx.lania.siralogin.srvnotificaciones.service.email;

import mx.lania.siralogin.srvcatalogos.models.Convocatoria;

public interface EmailSender {
    void send(String to, String email, String subject);

    String buildEmailRegistration(String name, String link);

    String buildEmailRegistroConvocatoria(String name, Convocatoria convocatoria);



}
