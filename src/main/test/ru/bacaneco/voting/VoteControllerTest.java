package ru.bacaneco.voting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.bacaneco.voting.controller.AbstractControllerTest;
import ru.bacaneco.voting.controller.VoteController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bacaneco.voting.TestUtil.httpBasicOf;
import static ru.bacaneco.voting.testhelper.MenuTestHelper.DATE_OF_2020_05_03;
import static ru.bacaneco.voting.testhelper.MenuTestHelper.MENU5_ID;
import static ru.bacaneco.voting.testhelper.UserTestHelper.*;
import static ru.bacaneco.voting.testhelper.VoteTestHelper.*;


public class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/votes/";

    @Autowired
    private VoteController controller;

    @Test
    void getAllForAuthUser() throws Exception {
        perform(get(REST_URL)
                .with(httpBasicOf(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.unmarshalAndMatchWith(USER2_VOTES));
    }

    @Test
    void getAllByDate() throws Exception {
        perform(get(REST_URL + "filter?date=" + DATE_OF_2020_05_03)
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.unmarshalAndMatchWith(VOTES_OF_2020_05_03));
    }

    @Test
    void getTodays() throws Exception {
        perform(get(REST_URL + "todays")
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.unmarshalAndMatchWith(TODAYS_VOTES));
    }

    @Test
    void vote() throws Exception {
        perform(put(REST_URL + "?menuId=" + MENU5_ID)
                .with(httpBasicOf(USER1)))
                .andExpect(status().isOk());
    }
}
