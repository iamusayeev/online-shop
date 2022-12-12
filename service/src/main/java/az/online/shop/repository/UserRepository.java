package az.online.shop.repository;

import az.online.shop.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User> {
    Optional<User> findByUsername(String username);
}