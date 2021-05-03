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
package de.gmasil.pokedexer.tesutils;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateSessionInterceptor implements ClientHttpRequestInterceptor {
    private String cookies;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        if (cookies != null) {
            request.getHeaders().add(HttpHeaders.COOKIE, cookies);
        }
        ClientHttpResponse response = execution.execute(request, body);
        if (cookies == null) {
            cookies = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        }
        return response;
    }
}
