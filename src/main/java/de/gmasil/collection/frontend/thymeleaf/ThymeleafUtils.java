package de.gmasil.collection.frontend.thymeleaf;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.gmasil.collection.card.Card;

@Service
public class ThymeleafUtils {
    @Value("${format.date:yyyy-MM-dd}")
    private String dateFormat;

    @Value("${format.currency.comma:false}")
    private boolean currencyComma;

    @Value("${format.currency.symbol:$}")
    private String currencySymbol;

    public String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public String formatCurrency(Double value) {
        if (value == null) {
            return "";
        }
        String s = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.US)).format(value);
        if (currencyComma) {
            s = s.replace('.', ',');
        }
        return s + currencySymbol;
    }

    public String formatProgress(int value) {
        return Card.PROGRESS_VALUES[value];
    }
}
