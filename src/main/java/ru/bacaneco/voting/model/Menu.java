package ru.bacaneco.voting.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Menu extends AbstractBaseEntity{
    private LocalDate date;

    private Restaurant restaurant;

    private List<Dish> dishes = Collections.emptyList();

    private boolean enabled = true;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant) {
        this.id = id;
        this.date = date;
        this.restaurant = restaurant;
    }

    public Menu(Integer id, LocalDate date, Restaurant restaurant, boolean enabled) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.enabled = enabled;
    }

    public Menu(Menu menu) {
        this.id = menu.getId();
        this.date = menu.getDate();
        this.restaurant = menu.getRestaurant();
        this.dishes = menu.getDishes();
        this.enabled = menu.isEnabled();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", date=" + date +
                ", restaurant=" + restaurant +
                ", dishes=" + dishes +
                ", enabled=" + enabled +
                '}';
    }
}
