package ru.bacaneco.voting.model;

public class Dish extends AbstractNamedEntity {
    private int price;
    private Menu menu;
    private boolean enabled = true;

    public Dish() {
    }

    public Dish(Integer id, String name, int price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }

    public Dish(Integer id, String name, int price, Menu menu, boolean enabled) {
        super(id, name);
        this.price = price;
        this.menu = menu;
        this.enabled = enabled;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", menuId=" + menu.id +
                ", enabled=" + enabled +
                '}';
    }
}
