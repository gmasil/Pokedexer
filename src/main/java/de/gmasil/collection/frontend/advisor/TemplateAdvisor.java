package de.gmasil.collection.frontend.advisor;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class TemplateAdvisor {
    @ModelAttribute
    public Template getTemplate(Model model) {
        return new Template(model);
    }
}
