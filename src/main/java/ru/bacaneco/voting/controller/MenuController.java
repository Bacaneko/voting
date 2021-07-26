package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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

@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController extends AbstractController {
    public final static String ENTITY_NAME = "menu";
    public static final String TODAYS_MENUS_CACHE_NAME = "todaysMenus";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository restaurantRepository;

    public MenuController(CacheManager cacheManager, MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        super(cacheManager, menuRepository);
        this.restaurantRepository = restaurantRepository;
    }

    @DeleteMapping("/{menuId}")
    public void deleteById(int id, @PathVariable int menuId) {
        log.info("Delete menu with id={}", id);
        menuRepository.deleteById(id);
    }

    @GetMapping("/history")
    public List<Menu> getAllByDate(LocalDate date) {
        log.info("Get menus by date: {}", date);
        return menuRepository.findAllByDate(date);
    }

    @GetMapping("/todays")
    @Cacheable(TODAYS_MENUS_CACHE_NAME)
    public List<Menu> getTodays() {
        LocalDate date = LocalDate.now();
        log.info("Get today's: {} menus", date);
        return menuRepository.getTodays(date);
    }

    @GetMapping("/{menuId}")
    public Menu getById(@PathVariable int menuId) {
        log.info("Get menu with id={}", menuId);
        Menu menu = menuRepository.findByIdWithRestaurantAndDishes(menuId);
        ValidationUtil.checkIsFound(menu != null);
        return menu;
    }


    @PatchMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable (@PathVariable int menuId, @RequestParam boolean enabled) {
        log.info("{} the menu with id {}", enabled ? "Enable" : "Disable", menuId);
        Menu menu = menuRepository.findById(menuId).orElseThrow();

        if (enabled) {
            Integer restaurantId = menu.getRestaurant().getId();
            ValidationUtil.checkIsEnabled(restaurantRepository.findByEnabledTrueAndId(restaurantId) != null,
                    restaurantId, RestaurantController.ENTITY_NAME);
        }
        menu.setEnabled(enabled);
        menuRepository.save(menu);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Menu> create(@RequestBody MenuTo menuTo) {
        ValidationUtil.checkIsNew(menuTo);

        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        ValidationUtil.checkIsEnabled(restaurant.isEnabled(), restaurantId, RestaurantController.ENTITY_NAME);


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
        log.info("Update menu with id={}", menuId);
        ValidationUtil.assureIdConsistency(menuTo, menuId);
        Menu oldMenu = menuRepository.findById(menuId).orElseThrow();
        ValidationUtil.checkIsPresentOrFuture(oldMenu);

        int restaurantId = menuTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository.findById(menuTo.getRestaurantId()).orElseThrow();
        ValidationUtil.checkIsEnabled(restaurant.isEnabled(), restaurantId, RestaurantController.ENTITY_NAME);

        Menu newMenu = MenuUtil.of(menuTo, restaurant);
        menuRepository.save(newMenu);

        if (!evictCacheIfTodays(newMenu)) {
            evictCacheIfTodays(oldMenu);
        }
    }

    private boolean evictCacheIfTodays(Menu menu) {
        if (menu.getDate().equals(LocalDate.now()) && !menu.getDishes().isEmpty()) {
            evictTodaysMenusCache();
            return true;
        }
        return false;
    }

    @Scheduled(cron = "0 0 0 * * *")
    void evictTodaysMenusCache() {
        log.info("Clear the cache of today's menus");
        evictCache();
    }

}
