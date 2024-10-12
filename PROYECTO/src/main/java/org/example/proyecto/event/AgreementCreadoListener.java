package org.example.proyecto.event;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;




import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;



@Component
public class AgreementCreadoListener {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async  // Hacerlo asíncrono para que no bloquee el flujo principal
    @EventListener
    public void manejarAgreementCreadoEvent(AgreementCreadoEvent event) throws MessagingException {
        // Obtener datos del acuerdo
        String emailInitiator = event.getAgreement().getInitiator().getEmail();
        String emailRecipient = event.getAgreement().getRecipient().getEmail();
        String itemIniName = event.getAgreement().getItem_ini().getName();
        String itemFinName = event.getAgreement().getItem_fin().getName();

        // Preparar el contexto de Thymeleaf
        Context context = new Context();
        context.setVariable("itemIniName", itemIniName);
        context.setVariable("itemFinName", itemFinName);
        context.setVariable("emailInitiator", emailInitiator);
        context.setVariable("emailRecipient", emailRecipient);

        // Procesar la plantilla HTML de Thymeleaf
        String contenidoHtml = templateEngine.process("agreement-created-email", context);

        // Enviar el correo al iniciador y al receptor del acuerdo
        enviarCorreo(emailInitiator, contenidoHtml);
        enviarCorreo(emailRecipient, contenidoHtml);

        System.out.println("Correos enviados a los usuarios involucrados en el acuerdo.");
    }

    private void enviarCorreo(String email, String contenidoHtml) throws MessagingException {
        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, "utf-8");

        helper.setTo(email);
        helper.setSubject("¡Nuevo Acuerdo Creado en MarketExchange!");
        helper.setText(contenidoHtml, true);

        mailSender.send(mensaje);
    }
}


