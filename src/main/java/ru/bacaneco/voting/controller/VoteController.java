package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bacaneco.voting.AuthenticatedUser;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.model.Vote;
import ru.bacaneco.voting.repository.MenuRepository;
import ru.bacaneco.voting.repository.VoteRepository;
import ru.bacaneco.voting.util.ValidationUtil;
import ru.bacaneco.voting.util.exception.IllegalVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    public static final LocalTime VOTE_UPDATE_DEADLINE = LocalTime.of(11, 0);


    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;


    public VoteController(MenuRepository menuRepository, VoteRepository voteRepository) {
        this.menuRepository = menuRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/filter")
    public List<Vote> getAllByDate(@RequestParam LocalDate date) {
        return getByDate(date);
    }
    @GetMapping("/todays")
    public List<Vote> getTodays() {
        return getByDate(LocalDate.now());
    }

    @GetMapping("/{voteId}")
    public List<Vote> getAllForAuthUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        int userId = authenticatedUser.getId();
        log.info("Get all votes of user by id={}", userId);

        return voteRepository.findAllByUserIdWithRestaurants(userId);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void vote(@RequestParam int menuId, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        LocalDate today = LocalDate.now();

        ValidationUtil.checkIsValidForVoting(menu, menuId, today);

        int userId = authenticatedUser.getId();
        Vote oldVote = voteRepository.getById(userId);

        if (oldVote == null) {
            log.info("User with id={} voted for menu with id={} and date={}", userId, menuId, today);

            voteRepository.save(new Vote(today, menu, authenticatedUser.getUser()));
        } else if (LocalTime.now().isBefore(VOTE_UPDATE_DEADLINE)) {
            log.info("User with id={} voted again on {}; old choice: menu with id={}, new choice: menu with id={}",
                    userId, today, oldVote.getMenu().getId(), menuId);

            oldVote.setMenu(menu);
        } else {
            throw new IllegalVoteException(String.format(
                    "It's not allowed to change a vote after %s", VOTE_UPDATE_DEADLINE));
        }
    }

    private List<Vote> getByDate(LocalDate date) {
        log.info("Get all votes for {}", date);
        return voteRepository.findAllByDateWithRestaurants(date);
    }
}
