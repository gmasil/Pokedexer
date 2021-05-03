/**
 * Pokédexer
 * Copyright © 2021 Gmasil
 *
 * This file is part of Pokédexer.
 *
 * Pokédexer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Pokédexer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Pokédexer.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.collection.setup;

import javax.servlet.http.HttpServletResponse;
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
    public String setup(Template template, User user) {
        if (userService.hasUsers()) {
            return template.makeSetupAlreadyDone();
        } else {
            return template.makeSetup();
        }
    }

    @PostMapping("/setup")
    public String setupPost(HttpServletResponse response, Template template, Model model, @Valid User user,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSetup();
        }
        userService.encodePassword(user);
        userService.save(user);
        return template.makeSetupAlreadyDone();
    }
}
