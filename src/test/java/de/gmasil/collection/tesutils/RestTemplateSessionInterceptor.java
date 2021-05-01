package de.gmasil.collection.tesutils;

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
