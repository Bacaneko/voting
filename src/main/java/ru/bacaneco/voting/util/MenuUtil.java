package ru.bacaneco.voting.util;

import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.model.Restaurant;
import ru.bacaneco.voting.to.MenuTo;

public final class MenuUtil {
    private MenuUtil() {
    }

    public static MenuTo getToFrom(Menu menu) {
        return new MenuTo(menu.getDate(), menu.getRestaurant().getId());
    }

    public static Menu of(MenuTo menuTo, Restaurant restaurant) {
        return new Menu(menuTo.getId(), menuTo.getDate(), restaurant);
    }
}