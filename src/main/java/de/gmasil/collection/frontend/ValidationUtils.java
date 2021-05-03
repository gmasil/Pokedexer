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
package de.gmasil.collection.frontend;

import java.lang.reflect.Field;

import org.springframework.validation.FieldError;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static boolean setErrorMessage(FieldError fieldError, String message) {
        try {
            Field field = FieldError.class.getSuperclass().getSuperclass().getDeclaredField("defaultMessage");
            field.setAccessible(true);
            field.set(fieldError, message);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    public static boolean handleException(FieldError fieldError) {
        if (fieldError != null) {
            return handleException(fieldError, fieldError.getField());
        }
        return true;
    }

    public static boolean handleException(FieldError fieldError, String fieldName) {
        if (fieldError != null && fieldError.isBindingFailure()) {
            String message;
            if (fieldError.getRejectedValue().toString().isEmpty()) {
                message = String.format("The %s may not be empty", fieldName);
            } else {
                message = String.format("The %s is invalid", fieldName);
            }
            return ValidationUtils.setErrorMessage(fieldError, message);
        }
        return true;
    }
}
