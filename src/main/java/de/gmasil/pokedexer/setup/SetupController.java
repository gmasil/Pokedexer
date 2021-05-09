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
 * along with Pokédexer. If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.pokedexer.setup;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import de.gmasil.pokedexer.controller.advisor.Template;
import de.gmasil.pokedexer.dto.UserDTO;
import de.gmasil.pokedexer.jpa.User;
import de.gmasil.pokedexer.jpa.UserService;
import de.gmasil.pokedexer.services.EntityMapper;

@Controller
public class SetupController {

    @Autowired
    private UserService userService;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping("/setup")
    public String setup(Template template, UserDTO userDTO) {
        if (userService.hasUsers()) {
            return template.makeSetupAlreadyDone();
        } else {
            return template.makeSetup();
        }
    }

    @PostMapping("/setup")
    public String setupPost(HttpServletResponse response, Template template, Model model, @Valid UserDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return template.makeSetup();
        }
        User user = entityMapper.mapUserDTO(userDTO);
        userService.encodePassword(user);
        userService.save(user);
        return template.makeSetupAlreadyDone();
    }
}
