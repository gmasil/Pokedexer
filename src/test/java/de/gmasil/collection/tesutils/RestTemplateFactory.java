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
package de.gmasil.collection.tesutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Lazy
@Component
public class RestTemplateFactory {
    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplateBuilder templateBuilder;

    @Value("${setup.user.name:#{null}}")
    private String username;

    @Value("${setup.user.password:#{null}}")
    private String password;

    public RestTemplate getRestTemplate() {
        return templateBuilder.interceptors(new RestTemplateSessionInterceptor()).build();
    }

    public RestTemplate getAuthenticatedRestTemplate() {
        RestTemplate template = getRestTemplate();
        template.postForObject(url("/login?username=" + username + "&password=" + password), null, String.class);
        return template;
    }

    private String url(String uri) {
        return "http://localhost:" + port + uri;
    }
}
