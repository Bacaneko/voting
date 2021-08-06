package ru.bacaneco.voting.repository;

import org.springframework.data.repository.CrudRepository;
import ru.bacaneco.voting.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
}
