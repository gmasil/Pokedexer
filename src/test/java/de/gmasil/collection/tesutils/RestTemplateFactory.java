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
