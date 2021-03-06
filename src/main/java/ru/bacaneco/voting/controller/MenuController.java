package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.model.Restaurant;
import ru.bacaneco.voting.repository.MenuRepository;
import ru.bacaneco.voting.repository.RestaurantRepository;
import ru.bacaneco.voting.to.MenuTo;
import ru.bacaneco.voting.util.MenuUtil;
import ru.bacaneco.voting.util.ValidationUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;

    public MenuController(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @DeleteMapping("/{menuId}")
    public void deleteById(int id, @PathVariable String menuId) {
        log.info("Delete menu with id={}", id);
        menuRepository.deleteById(id);
    }

    @GetMapping("/history")
    public List<Menu> getAllByDate(LocalDate date) {
        log.info("Get menus by date: {}", date);
        return menuRepository.findAllByDate(date);
    }

    @GetMapping("/todays")
    public List<Menu> getTodays() {
        LocalDate date = LocalDate.now();
        log.info("Get today's: {} menus", date);
        return menuRepository.getTodays(date);
    }

    @GetMapping("/{menuId}")
    public Menu getById(@PathVariable int menuId) {
        return menuRepository.findByIdWithRestaurantAndDishes(menuId);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Menu> create(@RequestBody MenuTo menuTo) {
        ValidationUtil.checkIsNew(menuTo);

        int restaurantId = menuTo.getRestaurantId();

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();;

        Menu newMenu = new Menu(MenuUtil.of(menuTo, restaurant));
        log.info("Create a new menu {}", newMenu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("menus/{id}")
                .buildAndExpand(newMenu.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newMenu);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody MenuTo menuTo, @PathVariable int menuId) {

        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(menuTo.getRestaurantId()).orElseThrow();

        Menu newMenu = MenuUtil.of(menuTo, restaurant);

        menuRepository.save(newMenu);
    }

}
