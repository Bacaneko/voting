package ru.bacaneco.voting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bacaneco.voting.model.Menu;
import ru.bacaneco.voting.repository.MenuRepository;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @DeleteMapping("/{menuId}")
    public void deleteById(int id) {
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


//    @PostMapping
//    @Transactional
//        TODO: change @RequestBody to menuTo
//    public ResponseEntity<Menu> create(@RequestBody Menu menu) {
//        TODO: create util method isNew()
//        int restaurantId = menu.getRestaurant().getId();
//
//        TODO:  restaurantRepository.findById
//
//        Menu newMenu = new Menu(menu.getId(), menu.getDate(), restaurant);
//
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("menus/{id}")
//                .buildAndExpand(newMenu.getId()).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(newMenu);
//    }



}
