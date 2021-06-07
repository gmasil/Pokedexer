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
package de.gmasil.pokedexer.psa;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class PsaService {

    public int getPopulation(String cert) {
        Document doc;
        try {
            doc = Jsoup.parse(new URL("https://www.psacard.com/cert/" + cert), 10000);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        doc.select("#mainContent h5:first-child");
        Optional<Element> optionalPopulationText = doc.getElementsByTag("a").stream()
                .filter(el -> "Population".equals(el.text())).findFirst();
        if (!optionalPopulationText.isPresent()) {
            throw new IllegalStateException("Population field not found for cert " + cert);
        }
        return Integer.parseInt(optionalPopulationText.get().parent().nextElementSibling().text().trim());
    }
}
