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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import de.gmasil.pokedexer.jpa.User;
import de.gmasil.pokedexer.jpa.UserRepository;
import de.gmasil.pokedexer.jpa.UserService;

@Component
public class InitialSetupRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${setup.user:false}")
    private boolean setupUser;

    @Value("${setup.user.name:#{null}}")
    private String username;

    @Value("${setup.user.password:#{null}}")
    private String password;

    @EventListener(ApplicationReadyEvent.class)
    public void setupInitialUser() {
        if (setupUser && userRepository.count() == 0 && username != null && password != null) {
            User user = User.builder().name(username).password(password).admin(true).build();
            userService.encodePassword(user);
            userService.save(user);
        }
    }
}
