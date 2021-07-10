package ru.bacaneco.voting.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public class DishTo extends AbstractTo{

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;

    @NotBlank
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    private String name;

    @PositiveOrZero
    private int price;

    @Positive
    private int menuId;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getMenuId() {
        return menuId;
    }

    @Override
    public String toString() {
        return "DishTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", menuId=" + menuId +
                '}';
    }
}
