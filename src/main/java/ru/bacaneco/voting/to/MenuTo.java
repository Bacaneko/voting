package ru.bacaneco.voting.to;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class MenuTo extends AbstractTo {

    @NotNull
    @FutureOrPresent
    private LocalDate date;

    @Positive
    private int restaurantId;

    public LocalDate getDate() {
        return date;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public MenuTo() {
    }

    public MenuTo(LocalDate date, int restaurantId) {
        this.date = date;
        this.restaurantId = restaurantId;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "id=" + id +
                ", date=" + date +
                ", restaurantId=" + restaurantId +
                '}';
    }
}
