package ru.bacaneco.voting.testhelper;

import ru.bacaneco.voting.TestMatcher;
import ru.bacaneco.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static ru.bacaneco.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.bacaneco.voting.testhelper.RestaurantTestHelper.*;

public class MenuTestHelper {
    public static TestMatcher<Menu> MENU_MATCHER = new TestMatcher<>(Menu.class, "dishes");

    public static final LocalDate DATE_OF_2020_05_03 = LocalDate.of(2020, 5, 3);
    public static final LocalDate TODAY = LocalDate.now();

    public static final int MENU1_ID = START_SEQ + 11;
    public static final int MENU2_ID = START_SEQ + 12;
    public static final int MENU3_ID = START_SEQ + 13;
    public static final int MENU4_ID = START_SEQ + 14;
    public static final int MENU5_ID = START_SEQ + 15;
    public static final int EMPTY_MENU_ID = START_SEQ + 16;
    public static final int DISABLED_MENU_ID = START_SEQ + 17;

    public static final Menu MENU1 = new Menu(MENU1_ID, DATE_OF_2020_05_03, RESTAURANT1);
    public static final Menu MENU2 = new Menu(MENU2_ID, DATE_OF_2020_05_03, RESTAURANT2);
    public static final Menu MENU4 = new Menu(MENU4_ID, TODAY, RESTAURANT1);
    public static final Menu MENU5 = new Menu(MENU5_ID, TODAY, RESTAURANT2);

    public static final List<Menu> ALL_TODAYS_MENUS = List.of(MENU4, MENU5);
    public static final List<Menu> ENABLED_TODAYS_MENUS = List.of(MENU4, MENU5);
    public static final List<Menu> ENABLED_NOT_EMPTY_TODAYS_MENUS = List.of(MENU5, MENU4);
}
