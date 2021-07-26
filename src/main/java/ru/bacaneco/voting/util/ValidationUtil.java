package ru.bacaneco.voting.util;

import ru.bacaneco.voting.HasId;
import ru.bacaneco.voting.controller.DishController;
import ru.bacaneco.voting.controller.MenuController;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.util.exception.IllegalOperationException;
import ru.bacaneco.voting.util.exception.IllegalRequestDataException;
import ru.bacaneco.voting.util.exception.IllegalVoteException;

import java.time.LocalDate;
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

    public static void checkIsEnabled(boolean enabled, int parentId, String parentEntityName) {
        if (!enabled) {
            throw new IllegalOperationException(String.format(
                    "Operation can't be performed because the %s with id %d is disabled",
                    parentEntityName, parentId));
        }
    }

    public static void checkIsValidForVoting(Menu menu, int menuId, LocalDate today) {
        if (!menu.getDate().equals(today)) {
            throw new IllegalVoteException(String.format(
                    "The %s with id %d can't be voted today as it's out-of-date", MenuController.ENTITY_NAME, menuId));
        }
        if (menu.getDishes().isEmpty()) {
            throw new IllegalVoteException(String.format(
                    "The %s with id %d can't be voted now as it contains no %s items",
                    MenuController.ENTITY_NAME, menuId, DishController.ENTITY_NAME));
        }
    }

    public static void checkIsPresentOrFuture(Menu menu) {
        if (menu.getDate().isBefore(LocalDate.now())) {
            throw new IllegalOperationException(String.format(
                    "Operation can't be performed because the affected menu (id %d) is expired", menu.getId()));
        }
    }
}
