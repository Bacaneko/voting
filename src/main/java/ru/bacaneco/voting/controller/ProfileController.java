package ru.bacaneco.voting.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.repository.UserRepository;
import ru.bacaneco.voting.util.UserUtil;
import ru.bacaneco.voting.AuthenticatedUser;
import ru.bacaneco.voting.model.User;
import ru.bacaneco.voting.to.UserTo;
import ru.bacaneco.voting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public User get(@AuthenticationPrincipal AuthenticatedUser authUser) {
        log.info("Get current user, id={}", authUser.getId());
        return authUser.getUser();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@RequestBody UserTo userTo) {
        log.info("Create new user from userTo {}", userTo);

        User user = UserUtil.of(userTo);
        User newUser = userRepository.save(passwordAndEmailProcessing(user));

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/profile").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(newUser);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody UserTo userTo, @AuthenticationPrincipal AuthenticatedUser authUser) {
        int authId = authUser.getId();
        log.info("Update user with id={} as {}", authId, userTo);

        ValidationUtil.assureIdConsistency(userTo, authId);

        User user = UserUtil.of(userTo);
        userRepository.save(passwordAndEmailProcessing(user));
    }

    private User passwordAndEmailProcessing(User user) {
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

}
