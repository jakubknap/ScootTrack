package pl.scoottrack.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String firstname, EmailTemplateName emailTemplate, String confirmationUrl, String activationCode, String subject) {
        try {
            String templateName = isNull(emailTemplate) ? "confirm-email" : emailTemplate.getName();

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());

            Map<String, Object> variables = new HashMap<>();
            variables.put("firstname", firstname);
            variables.put("confirmationUrl", confirmationUrl);
            variables.put("activationCode", activationCode);

            Context context = new Context();
            context.setVariables(variables);

            helper.setFrom("contact@scoottrack.pl");
            helper.setTo(to);
            helper.setSubject(subject);

            String template = templateEngine.process(templateName, context);

            helper.setText(template, true);

            mailSender.send(mimeMessage);
            log.info("Email sent");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}