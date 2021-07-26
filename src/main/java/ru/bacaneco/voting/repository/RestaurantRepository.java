package ru.bacaneco.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bacaneco.voting.model.Restaurant;



public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findByEnabledTrueAndId(int id);
}
