package de.gmasil.collection.setup;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.gmasil.collection.frontend.advisor.Template;
import de.gmasil.collection.security.User;
import de.gmasil.collection.security.UserService;

@Controller
public class SetupController {
    @Autowired
    private UserService userService;

    @GetMapping("/setup")
    public String setup(Template template, Model model, User user) {
        if (userService.hasUsers()) {
            return template.makeSetupAlreadyDone();
        } else {
            return template.makeSetup();
        }
    }

    @PostMapping("/setup")
    public String setupPost(Template template, Model model, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSetup();
        }
        userService.encodePassword(user);
        userService.save(user);
        return template.makeSetupSuccess();
    }
}
