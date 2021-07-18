package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.bacaneco.voting.model.Vote;
import ru.bacaneco.voting.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/votes", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteRepository voteRepository;


    public VoteController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping("/filter")
    public List<Vote> getAllByDate(@RequestParam LocalDate date) {
        return voteRepository.findAllByDateWithRestaurants(date);
    }

    @GetMapping("/todays")
    public List<Vote> getByDate() {
        return voteRepository.findAllByDateWithRestaurants(LocalDate.now());
    }
//    TODO: add spring security and implementation with authorized users
//    @GetMapping("/{voteId}")
//    public List<Vote> getAllForAuthUser(int userId) {
//        log.info("Get all votes of user by id={}", userId);
//        return voteRepository.findAllByUserIdWithRestaurants(userId);
//    }
//
//    @PutMapping
//    @ResponseStatus(HttpStatus.OK)
//    @Transactional
//    public void vote() {
//
//    }


}
