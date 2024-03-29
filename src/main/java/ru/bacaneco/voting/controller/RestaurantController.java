package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.repository.MenuRepository;
import ru.bacaneco.voting.repository.RestaurantRepository;
import ru.bacaneco.voting.model.Restaurant;
import ru.bacaneco.voting.util.ValidationUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractController {
    public final static String ENTITY_NAME = "restaurant";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(CacheManager cacheManager, MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        super(cacheManager, menuRepository);
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getById(@PathVariable int restaurantId) {
        log.info("Get restaurant by id={}", restaurantId);
        return restaurantRepository.findById(restaurantId).orElseThrow();
    }

    @DeleteMapping("/{restaurantId}")
    public void deleteById(@PathVariable int restaurantId) {
        log.info("Delete restaurant by id={}", restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }

    @GetMapping("/history")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @PatchMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int restaurantId, @RequestParam boolean enabled) {
        log.info("{} the restaurant with id {}", enabled ? "Enable" : "Disable", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();

        restaurant.setEnabled(enabled);
        restaurantRepository.save(restaurant);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
        ValidationUtil.checkIsNew(restaurant);

        Restaurant newRestaurant = restaurantRepository.save(restaurant);
        log.info("Create new restaurant={}", newRestaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("restaurant/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newRestaurant);
    }

    @PutMapping(value = "/{restaurantId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = MenuController.TODAYS_MENUS_CACHE_NAME, allEntries = true)
    public void update(@RequestBody Restaurant restaurant, @PathVariable int restaurantId) {
        log.info("Update restaurant by id={}", restaurantId);
        ValidationUtil.assureIdConsistency(restaurant, restaurantId);
        ValidationUtil.checkIsFound(restaurantRepository.existsById(restaurantId));

        restaurantRepository.save(restaurant);
    }

}
