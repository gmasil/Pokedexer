package de.gmasil.collection.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class UnicodePropertySourceLoader implements PropertySourceLoader {

    @Override
    public String[] getFileExtensions() {
        return new String[] { "properties" };
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
        if (!properties.isEmpty()) {
            return Arrays.asList(new PropertiesPropertySource(name, properties));
        }
        return new LinkedList<>();
    }
}
