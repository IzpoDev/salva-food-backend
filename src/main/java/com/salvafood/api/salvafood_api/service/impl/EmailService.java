package com.salvafood.api.salvafood_api.service.impl;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    // Inyectamos el email del remitente desde las propiedades
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendEmailResetPassword(String to, String subject, String token) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(new InternetAddress( fromEmail,"Soporte SalvaFood"));
            helper.setTo(to);
            helper.setSubject(subject);

            String text = buildingEmailContent(token);
            helper.setText(text, true);

            javaMailSender.send(message);
            log.info("Email de reseteo de contraseña enviado correctamente a {}", to);
        } catch (Exception e) {
            // CORRECCIÓN: Mejorar el log para mostrar la traza completa del error.
            // Esto nos dará la causa raíz exacta si el problema persiste.
            log.error("Error al enviar el email a {}. Causa: {}", to, e.getMessage(), e);
            // Relanzar la excepción para que la transacción falle si es necesario.
            throw new RuntimeException("No se pudo enviar el correo de verificación.", e);
        }
    }

    public String buildingEmailContent(String token) {
        // Tu plantilla HTML está bien, no necesita cambios.
        String template = """
               <html>
                <body style="margin: 0; padding: 0; font-family: Arial, sans-serif;">
                  <div style="width: 80%; margin: auto; font-family: Arial, sans-serif;">
                    <div style="background-color: #2C7865; color: white; padding: 20px; text-align: center;">
                        <img src="https://scontent.ftru8-1.fna.fbcdn.net/v/t39.30808-6/505659208_122097789284906720_5364972285519875632_n.jpg?_nc_cat=108&ccb=1-7&_nc_sid=6ee11a&_nc_eui2=AeEuHfuAAIndjcXjTKMWE7TJbfgjOUZ-G2Rt-CM5Rn4bZHwGgIwqkT18bwiM8_mtPn9HEs1-NHdJz3PZP3Zj_89K&_nc_ohc=uWIDUCjt5bwQ7kNvwERyvlb&_nc_oc=Adnw-LsBin3KZVIkTXDSosDf4NGFKMCZgiwXZRyF_ESOSDxXpQvDsMdcG3NvoMOMNV4&_nc_zt=23&_nc_ht=scontent.ftru8-1.fna&_nc_gid=p-3YcERjAGkzwyHB0ltK2w&oh=00_AfRpdXL-CwGHf0prchDE5OrKTWo-_xjBivh4vQGVPFcQmQ&oe=6888D621"
                             alt="imagen de logo de SalvaFood" style="width: 200px; height: auto; max-width: 100%;">
                        <h1 style="font-size: 24px; font-weight: bold; color: white; margin: 10px 0 0 0;">SalvaFood Account - Reset Password</h1>
                    </div>
                    <div style="padding: 20px; text-align: center;">
                        <p style="font-size: 16px; color: #000; font-weight: bold;">Este es tu codigo de verificación:</p>
                        <p style="font-size: 20px; color: #000; background-color: #f0f0f0; padding: 10px; border-radius: 5px;"> <strong>{TOKEN}</strong></p>
                    </div>
                    <div style="background-color: #2C7865; color: white; padding: 20px; text-align: center;">
                        <p style="font-size: 14px; color: #fff; font-weight: normal;">Si no has solicitado este cambio, por favor ignora este mensaje.</p>
                        <p style="font-size: 14px; color: #fff; font-weight: normal;">Gracias por usar SalvaFood!</p>
                    </div>
                  </div>
                </body>
                </html>
                """;
        return template.replace("{TOKEN}",token);
    }
}