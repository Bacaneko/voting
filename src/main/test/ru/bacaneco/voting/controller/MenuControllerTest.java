package ru.bacaneco.voting.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bacaneco.voting.TestUtil.httpBasicOf;
import static ru.bacaneco.voting.testhelper.MenuTestHelper.*;
import static ru.bacaneco.voting.testhelper.UserTestHelper.ADMIN1;

public class MenuControllerTest extends AbstractControllerTest{

    private static final String REST_URL = "/menus/";

    @Autowired
    private MenuController controller;

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MENU1_ID)
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MENU_MATCHER.unmarshalAndMatchWith(MENU1));
    }

    @Test
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "history?date=" + TODAY)
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.unmarshalAndMatchWith(ALL_TODAYS_MENUS));
    }

    @Test
    void getEnabledByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "?date=" + TODAY)
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.unmarshalAndMatchWith(ENABLED_TODAYS_MENUS));
    }

    @Test
    void getTodays() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "todays"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.unmarshalAndMatchWith(ENABLED_NOT_EMPTY_TODAYS_MENUS));
    }

    @Test
    void deleteById() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + EMPTY_MENU_ID)
                .with(httpBasicOf(ADMIN1)))
                .andExpect(status().isNoContent());
        assertThrows(NoSuchElementException.class, () -> controller.getById(EMPTY_MENU_ID));
    }


}
