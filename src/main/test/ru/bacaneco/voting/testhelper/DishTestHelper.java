package ru.bacaneco.voting.testhelper;

import ru.bacaneco.voting.TestMatcher;
import ru.bacaneco.voting.model.Dish;

import static ru.bacaneco.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.bacaneco.voting.testhelper.MenuTestHelper.*;

public class DishTestHelper {
    public static TestMatcher<Dish> DISH_MATCHER = new TestMatcher<>(Dish.class);

    public static final int DISH1_ID = START_SEQ + 14;
    public static final int DISH2_ID = START_SEQ + 15;
    public static final int DISH3_ID = START_SEQ + 16;
    public static final int DISH4_ID = START_SEQ + 17;
    public static final int DISH5_ID = START_SEQ + 18;
    public static final int DISH6_ID = START_SEQ + 19;
    public static final int DISH7_ID = START_SEQ + 20;
    public static final int DISH8_ID = START_SEQ + 21;
    public static final int DISH9_ID = START_SEQ + 22;
    public static final int DISH10_ID = START_SEQ + 23;
    public static final int DISH11_ID = START_SEQ + 24;
    public static final int DISH12_ID = START_SEQ + 25;
    public static final int DISH13_ID = START_SEQ + 26;

    public static final Dish DISH1 = new Dish(DISH1_ID, "Turkish Dish 1", 20_000, MENU1);
    public static final Dish DISH2 = new Dish(DISH2_ID, "Turkish Dish 3", 15_000, MENU1);
    public static final Dish DISH3 = new Dish(DISH3_ID, "Turkish Dish 2", 35_000, MENU1);
    public static final Dish DISH4 = new Dish(DISH4_ID, "Japanese Dish 1", 35_000, MENU2);
    public static final Dish DISH7 = new Dish(DISH7_ID, "Moldovan Dish 1", 20_000, MENU4);
    public static final Dish DISH8 = new Dish(DISH8_ID, "Moldovan Dish 5", 30_000, MENU4);
    public static final Dish DISH9 = new Dish(DISH9_ID, "Moldovan Dish 4", 40_000, MENU4);
    public static final Dish DISH10 = new Dish(DISH10_ID, "Japanese Dish 2", 75_000, MENU5);
    public static final Dish DISH11 = new Dish(DISH11_ID, "Japanese Dish 3", 55_000, MENU5);
    public static final Dish DISH12 = new Dish(DISH12_ID, "Japanese Dish 5", 42_000, MENU5);
    public static final Dish DISH13 = new Dish(DISH13_ID, "Japanese Dish 4", 30_000, MENU5);

}
