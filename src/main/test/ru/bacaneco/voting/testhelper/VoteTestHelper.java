package ru.bacaneco.voting.testhelper;

import ru.bacaneco.voting.TestMatcher;
import ru.bacaneco.voting.model.Vote;

import java.util.List;

import static ru.bacaneco.voting.model.AbstractBaseEntity.START_SEQ;
import static ru.bacaneco.voting.testhelper.MenuTestHelper.*;
import static ru.bacaneco.voting.testhelper.UserTestHelper.*;

public class VoteTestHelper {
    public static TestMatcher<Vote> VOTE_MATCHER = new TestMatcher<>(Vote.class, "menu", "user");

    public static final int VOTE1_ID = START_SEQ + 27;
    public static final int VOTE2_ID = START_SEQ + 28;
    public static final int VOTE3_ID = START_SEQ + 29;
    public static final int VOTE4_ID = START_SEQ + 30;
    public static final int VOTE5_ID = START_SEQ + 31;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, DATE_OF_2020_05_03, MENU1, USER1);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, DATE_OF_2020_05_03, MENU2, USER3);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, DATE_OF_2020_05_03, MENU2, USER2);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, TODAY, MENU4, USER2);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, TODAY, MENU4, USER3);

    public static final List<Vote> USER2_VOTES = List.of(VOTE3, VOTE4);
    public static final List<Vote> VOTES_OF_2020_05_03 = List.of(VOTE1, VOTE2, VOTE3);
    public static final List<Vote> TODAYS_VOTES = List.of(VOTE4, VOTE5);
}
