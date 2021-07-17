package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.model.Dish;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.repository.DishRepository;
import ru.bacaneco.voting.repository.MenuRepository;
import ru.bacaneco.voting.to.DishTo;
import ru.bacaneco.voting.util.DishUtil;
import ru.bacaneco.voting.util.ValidationUtil;

import java.net.URI;

@RestController
@RequestMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishRepository dishRepository;

    private final MenuRepository menuRepository;

    public DishController(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping("/{dishId}")
    public Dish getById(@PathVariable int dishId) {
        log.info("Get dish by id={}", dishId);

        return dishRepository.findById(dishId).orElseThrow();
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable int dishId) {
        log.info("Delete dish by id={}", dishId);

        dishRepository.deleteById(dishId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Dish> create(@RequestBody DishTo dishTo) {
        ValidationUtil.checkIsNew(dishTo);

        Menu menu = menuRepository.findById(dishTo.getMenuId()).orElseThrow();

        Dish newDish = DishUtil.of(dishTo, menu);
        log.info("Create new dish={}", newDish);

        URI uriOfNewResources = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("dishes/{id}")
                .buildAndExpand(newDish.getId()).toUri();

        return ResponseEntity.created(uriOfNewResources).body(newDish);
    }

//    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    @Transactional
//    public void update(@RequestBody DishTo dishTo, @PathVariable int dishId) {
//        log.info("Update dish with id={}", dishId);
//
//        ValidationUtil.assureIdConsistency(dishTo, dishId);
//
//        Dish oldDish = dishRepository.findByIdWithMenu(dishId);
//
//        Menu menu = oldDish.getMenu();
//
//        Dish newDish = DishUtil.of(dishTo, menu);
//        dishRepository.save(newDish);
//    }


}
