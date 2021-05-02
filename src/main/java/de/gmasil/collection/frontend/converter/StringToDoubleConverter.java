package de.gmasil.collection.frontend.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDoubleConverter implements Converter<String, Double> {
    @Override
    public Double convert(String source) {
        return Double.parseDouble(source.replace(',', '.'));
    }
}
