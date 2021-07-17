package ru.bacaneco.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bacaneco.voting.model.Dish;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    void delete(int id);
//    TODO: resolve problem with d.id=:id
//    @Query("SELECT d FROM Dish d JOIN FETCH d.menu WHERE d.id=:id")
//    Dish findByIdWithMenu(int dishId);
}
