/**
 * Tupugoya
 * Copyright © 2019 Simon Oelerich
 *
 * This file is part of Tupugoya.
 *
 * Tupugoya is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tupugoya is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tupugoya.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.gmasil.collection.frontend.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDate> {
	@Override
	public LocalDate convert(String source) {
		if (source.isEmpty()) {
			return null;
		}
		return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
	}
}
