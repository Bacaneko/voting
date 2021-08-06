package ru.bacaneco.voting.testhelper;

import ru.bacaneco.voting.TestMatcher;
import ru.bacaneco.voting.model.Role;
import ru.bacaneco.voting.model.User;

import static ru.bacaneco.voting.model.AbstractBaseEntity.START_SEQ;

public class UserTestHelper {
    public static TestMatcher<User> USER_MATCHER = new TestMatcher<>(User.class, "password", "registered");

    public static final int USER1_ID = START_SEQ + 1;
    public static final int USER2_ID = START_SEQ;
    public static final int USER3_ID = START_SEQ + 4;
    public static final int ADMIN1_ID = START_SEQ + 3;

    public static final User USER1 = new User(USER1_ID, "User 1", "user1@gmail.com", "password1", Role.USER);
    public static final User USER2 = new User(USER2_ID, "User 2", "user2@gmail.com", "password2", Role.USER);
    public static final User USER3 = new User(USER3_ID, "User 3", "user3@gmail.com", "password3", Role.USER);

    public static final User ADMIN1 = new User(ADMIN1_ID, "Admin 1", "admin1@gmail.com", "admin1", Role.ADMIN);
}
