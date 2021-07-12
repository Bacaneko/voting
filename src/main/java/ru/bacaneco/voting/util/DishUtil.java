package ru.bacaneco.voting.util;

import ru.bacaneco.voting.model.Dish;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.to.DishTo;


public class DishUtil {
    private DishUtil() {

    }

    public static Dish of(DishTo dishTo, Menu menu) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), menu);
    }
}
