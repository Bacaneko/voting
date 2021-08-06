package ru.bacaneco.voting.testhelper;

import ru.bacaneco.voting.TestMatcher;
import ru.bacaneco.voting.model.AbstractBaseEntity;
import ru.bacaneco.voting.model.Restaurant;

public class RestaurantTestHelper {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = new TestMatcher<>(Restaurant.class);

    public static final int RESTAURANT1_ID = AbstractBaseEntity.START_SEQ + 5;
    public static final int RESTAURANT2_ID = AbstractBaseEntity.START_SEQ + 6;
    public static final int RESTAURANT3_ID = AbstractBaseEntity.START_SEQ + 7;

    public static final Restaurant RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Turkish");
    public static final Restaurant RESTAURANT2 = new Restaurant(RESTAURANT2_ID, "Moldovan");
    public static final Restaurant RESTAURANT3 = new Restaurant(RESTAURANT3_ID, "Japanese");

    public static final Restaurant[] RESTAURANTS = new Restaurant[]{RESTAURANT1, RESTAURANT2, RESTAURANT3};

    public static Restaurant getNew() {
        return new Restaurant("New restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "Turkish Updated");
    }
}
