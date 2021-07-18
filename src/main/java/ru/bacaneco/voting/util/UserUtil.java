package ru.bacaneco.voting.util;

import ru.bacaneco.voting.model.Role;
import ru.bacaneco.voting.model.User;
import ru.bacaneco.voting.to.UserTo;

import java.time.Instant;

public class UserUtil {
    private UserUtil() {
    }

    public static User of(UserTo userTo) {
        return new User(userTo.getId(), userTo.getName(), userTo.getEmail(), userTo.getPassword(),
                Role.USER, Instant.now(), true);
    }
}
