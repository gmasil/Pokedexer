package de.gmasil.collection.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import de.gmasil.collection.frontend.advisor.Template;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Template template) {
        return template.makeLogin();
    }
}
