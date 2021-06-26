package ru.bacaneco.voting.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity{

    private LocalDate date;

    private Menu menu;

    private User user;

    public Vote() {
    }

    public Vote(LocalDate date, Menu menu, User user) {
        this.date = date;
        this.menu = menu;
        this.user = user;
    }

    public Vote(Integer id, LocalDate date, Menu menu, User user) {
        super(id);
        this.date = date;
        this.menu = menu;
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ". date=" + date +
                ", menu=" + menu +
                ", user=" + user +
                '}';
    }
}
