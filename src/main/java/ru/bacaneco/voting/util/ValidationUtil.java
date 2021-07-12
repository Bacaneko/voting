package ru.bacaneco.voting.util;

import ru.bacaneco.voting.HasId;
import ru.bacaneco.voting.util.exception.IllegalRequestDataException;

import java.util.NoSuchElementException;

public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkIsNew(HasId object) {
        if (!object.isNew()) {
            throw new IllegalRequestDataException(
                    object.getClass().getSimpleName().replaceFirst("To", "") + " id must be null!");
        }
    }

    public static void assureIdConsistency(HasId object, int id) {
        if (object.isNew()) {
            object.setId(id);
        } else if (object.getId() != id) {
            throw new IllegalRequestDataException(String.format(
                    "%s must be with id=%d!",
                    object.getClass().getSimpleName().replaceFirst("To", ""),
                    id));
        }
    }

    public static void checkIsFound(boolean found) {
        if (!found) {
            throw new NoSuchElementException();
        }
    }
}
