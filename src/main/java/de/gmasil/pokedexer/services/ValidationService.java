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
package de.gmasil.pokedexer.services;

import java.lang.reflect.Field;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class ValidationService {
    public boolean setErrorMessage(FieldError fieldError, String message) {
        try {
            Field field = FieldError.class.getSuperclass().getSuperclass().getDeclaredField("defaultMessage");
            field.setAccessible(true);
            field.set(fieldError, message);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    public boolean handleException(FieldError fieldError) {
        if (fieldError != null) {
            return handleException(fieldError, fieldError.getField());
        }
        return true;
    }

    public boolean handleException(FieldError fieldError, String fieldName) {
        if (fieldError != null && fieldError.isBindingFailure()) {
            String message;
            if (fieldError.getRejectedValue().toString().isEmpty()) {
                message = String.format("The %s may not be empty", fieldName);
            } else {
                message = String.format("The %s is invalid", fieldName);
            }
            return setErrorMessage(fieldError, message);
        }
        return true;
    }
}
