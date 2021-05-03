/**
 * Collection
 * Copyright © 2021 Gmasil
 *
 * This file is part of Collection.
 *
 * Collection is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collection is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collection.  If not, see <https://www.gnu.org/licenses/>.
 */
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
