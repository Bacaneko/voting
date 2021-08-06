package ru.bacaneco.voting.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bacaneco.voting.TestUtil;
import ru.bacaneco.voting.controller.json.JsonUtil;
import ru.bacaneco.voting.model.User;
import ru.bacaneco.voting.repository.UserRepository;
import ru.bacaneco.voting.to.UserTo;
import ru.bacaneco.voting.util.UserUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.bacaneco.voting.TestUtil.httpBasicOf;
import static ru.bacaneco.voting.testhelper.UserTestHelper.USER1;
import static ru.bacaneco.voting.testhelper.UserTestHelper.USER_MATCHER;

public class ProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/profile/";

    @Autowired
    private ProfileController controller;

    @Autowired
    private UserRepository repository;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(httpBasicOf(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.unmarshalAndMatchWith(USER1));
    }


    @Test
    void register() throws Exception {
        UserTo newUserTo = new UserTo("New User", "newuser@gmail.com", "newpassword");
        User newUser = UserUtil.of(newUserTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + "/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newUserTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        User created = TestUtil.readFromResultAction(action, User.class);
        int newId = created.getId();
        newUser.setId(newId);

        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.findById(newId).orElse(null), newUser);
    }
}
