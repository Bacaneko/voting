package ru.bacaneco.voting.controller;

import org.springframework.cache.CacheManager;
import ru.bacaneco.voting.repository.MenuRepository;

import java.util.Objects;

import static ru.bacaneco.voting.controller.MenuController.TODAYS_MENUS_CACHE_NAME;

public class AbstractController {
    protected final CacheManager cacheManager;
    protected final MenuRepository menuRepository;

    public AbstractController(CacheManager cacheManager, MenuRepository menuRepository) {
        this.cacheManager = cacheManager;
        this.menuRepository = menuRepository;
    }

    protected void evictCache() {
        Objects.requireNonNull(cacheManager.getCache(TODAYS_MENUS_CACHE_NAME)).clear();
    }
}