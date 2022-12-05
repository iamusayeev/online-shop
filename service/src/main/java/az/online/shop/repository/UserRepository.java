package az.online.shop.repository;

import az.online.shop.entity.User;
import com.querydsl.core.types.Predicate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Integer>, QuerydslPredicateExecutor<User>, JpaSpecificationExecutor<User> {

    List<User> findAll(Predicate predicate);

    Optional<User> findByUsername(String username);
}