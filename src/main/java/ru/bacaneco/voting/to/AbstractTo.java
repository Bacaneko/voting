package ru.bacaneco.voting.to;

import ru.bacaneco.voting.HasId;

public abstract class AbstractTo implements HasId {
    protected Integer id;

    public AbstractTo() {

    }

    public AbstractTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
