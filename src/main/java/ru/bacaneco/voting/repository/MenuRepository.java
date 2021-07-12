package ru.bacaneco.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bacaneco.voting.model.Menu;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository  extends JpaRepository<Menu, Integer> {

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dishes WHERE m.date=:date")
    List<Menu> findAllByDate(LocalDate date);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant LEFT JOIN FETCH m.dishes WHERE m.id=:id")
    Menu findByIdWithRestaurantAndDishes(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int deleteById(int id);

    @Query("SELECT DISTINCT m FROM Menu m JOIN FETCH m.restaurant JOIN FETCH m.dishes d WHERE m.date=:date AND d.enabled=true")
    List<Menu> getTodays(LocalDate date);
}
