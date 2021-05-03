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
package de.gmasil.collection.setup;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import de.gmasil.collection.security.UserService;

@Service
public class InitialSetupInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (!userService.hasUsers() //
                && !request.getRequestURI().startsWith("/setup") //
                && !request.getRequestURI().startsWith("/error") //
                && !request.getRequestURI().startsWith("/public/")) {//
            response.sendRedirect("/setup");
            return false;
        }
        return true;
    }
}
