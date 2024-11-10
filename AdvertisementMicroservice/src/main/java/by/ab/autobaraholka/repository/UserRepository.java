package by.ab.autobaraholka.repository;

import by.ab.autobaraholka.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
}