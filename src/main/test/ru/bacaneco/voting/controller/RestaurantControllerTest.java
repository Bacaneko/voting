package ru.bacaneco.voting.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bacaneco.voting.TestUtil;
import ru.bacaneco.voting.controller.json.JsonUtil;

import ru.bacaneco.voting.model.Restaurant;
import ru.bacaneco.voting.testhelper.RestaurantTestHelper;
import java.util.Arrays;

import static ru.bacaneco.voting.testhelper.RestaurantTestHelper.*;
import static ru.bacaneco.voting.testhelper.UserTestHelper.ADMIN1;
import static ru.bacaneco.voting.testhelper.UserTestHelper.USER1_ID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/restaurants/";

    @Autowired
    private RestaurantController controller;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.unmarshalAndMatchWith(Arrays.asList(RESTAURANTS)));
    }

    @Test
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.unmarshalAndMatchWith(RESTAURANTS[0]));
    }

    @Test
    void deleteById() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID)
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> controller.getById(RESTAURANT1_ID));
    }

    @Test
    void create() throws Exception {
        Restaurant newRestaurant = RestaurantTestHelper.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant))
                .with(TestUtil.httpBasicOf(ADMIN1)));

        Restaurant created = TestUtil.readFromResultAction(action, Restaurant.class);
        int newId = created.getId();
        newRestaurant.setId(newId);

        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(controller.getById(newId), newRestaurant);
    }

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestHelper.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(controller.getById(RESTAURANT1_ID), updated);

    }

    //    Tests for NotFoundException --------------------------------------------------------------------------------------

    @Test
    void getByIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER1_ID)
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteByIdNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER1_ID)
                .with(TestUtil.httpBasicOf(ADMIN1)))
                .andExpect(status().isUnprocessableEntity());
    }

}
