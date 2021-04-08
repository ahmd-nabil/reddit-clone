package services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailBuilder {
    private final TemplateEngine templateEngine;

    public MailBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String username, String verificationLink) {
        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("verificationLink", verificationLink);
        return templateEngine.process("mail-template", context);
    }
}
