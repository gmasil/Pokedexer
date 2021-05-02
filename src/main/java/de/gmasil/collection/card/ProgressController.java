package de.gmasil.collection.card;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import de.gmasil.collection.frontend.advisor.Template;

@RestController
public class ProgressController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @RequestMapping(value = "/api/progress/{value}", method = RequestMethod.GET, produces = "image/svg+xml")
    public String getProgressImage(Template template, @PathVariable int value, HttpServletResponse response) {
        Context context = new Context();
        context.setVariable("svgprogress", value);
        return templateEngine.process("fragments/progress", context);
    }
}
