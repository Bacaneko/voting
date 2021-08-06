package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.repository.DishRepository;
import ru.bacaneco.voting.repository.MenuRepository;
import ru.bacaneco.voting.model.Dish;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.to.DishTo;
import ru.bacaneco.voting.util.DishUtil;
import ru.bacaneco.voting.util.ValidationUtil;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/dishes", produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController extends AbstractController {
    public final static String ENTITY_NAME = "dish";


    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DishRepository dishRepository;

    public DishController(CacheManager cacheManager, DishRepository dishRepository, MenuRepository menuRepository) {
        super(cacheManager, menuRepository);
        this.dishRepository = dishRepository;
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

        Menu menu = menuRepository.findByDishId(dishId);
        ValidationUtil.checkIsFound(menu != null);
        ValidationUtil.checkIsPresentOrFuture(menu);

        dishRepository.deleteById(dishId);
    }

    @PatchMapping("/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void enable(@PathVariable int dishId, @RequestParam boolean enabled) {
        log.info("{} the dish with id {}", enabled ? "Enable" : "Disable", dishId);

        Dish dish = dishRepository.findByIdWithMenu(dishId);
        ValidationUtil.checkIsFound(dish != null);
        ValidationUtil.checkIsEnabled(dish.isEnabled(), dishId, ENTITY_NAME);

        Menu menu = dish.getMenu();
        ValidationUtil.checkIsEnabled(menu.isEnabled(), menu.getId(), ENTITY_NAME);
        ValidationUtil.checkIsPresentOrFuture(menu);

        if (enabled) {
            Integer menuId = dish.getMenu().getId();
            ValidationUtil.checkIsEnabled(menuRepository.findByEnabledTrueAndId(menuId) != null,
                    menuId, MenuController.ENTITY_NAME);
        }
        dish.setEnabled(enabled);
        dishRepository.save(dish);

        evictCacheIfTodays(dish.getMenu());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Dish> create(@RequestBody DishTo dishTo) {
        ValidationUtil.checkIsNew(dishTo);

        int menuId = dishTo.getMenuId();
        Menu menu = menuRepository.findById(menuId).orElseThrow();
        ValidationUtil.checkIsEnabled(menu.isEnabled(), menuId, MenuController.ENTITY_NAME);
        ValidationUtil.checkIsPresentOrFuture(menu);

        Dish newDish = DishUtil.of(dishTo, menu);
        log.info("Create new dish={}", newDish);

        URI uriOfNewResources = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("dishes/{id}")
                .buildAndExpand(newDish.getId()).toUri();

        return ResponseEntity.created(uriOfNewResources).body(newDish);
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@RequestBody DishTo dishTo, @PathVariable int dishId) {
        log.info("Update dish with id={}", dishId);
        ValidationUtil.assureIdConsistency(dishTo, dishId);

        Dish oldDish = dishRepository.findByIdWithMenu(dishId);
        ValidationUtil.checkIsFound(oldDish != null);
        ValidationUtil.checkIsEnabled(oldDish.isEnabled(), dishId, ENTITY_NAME);

        Menu menu = oldDish.getMenu();
        Integer menuId = menu.getId();
        ValidationUtil.checkIsEnabled(menu.isEnabled(), menuId, ENTITY_NAME);
        ValidationUtil.checkIsPresentOrFuture(menu);

        int newMenuId = dishTo.getMenuId();
        if (newMenuId != menuId) {
            Menu newMenu = menuRepository.findById(newMenuId).orElseThrow();
            ValidationUtil.checkIsEnabled(newMenu.isEnabled(), newMenuId, MenuController.ENTITY_NAME);
            ValidationUtil.checkIsPresentOrFuture(newMenu);
            menu = newMenu;
        }

        Dish newDish = DishUtil.of(dishTo, menu);
        dishRepository.save(newDish);

        if (!evictCacheIfTodays(menu) && newMenuId != menuId) {
            evictCacheIfTodays(oldDish.getMenu());
        }
    }

    private boolean evictCacheIfTodays(Menu newMenu) {
        if (newMenu.getDate().equals(LocalDate.now())) {
            log.info("Clear the cache of today's menus");
            evictCache();
            return true;
        }
        return false;
    }


}
