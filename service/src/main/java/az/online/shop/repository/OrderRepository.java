package az.online.shop.repository;

import az.online.shop.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderRepository extends JpaRepository<Order, Integer>, QuerydslPredicateExecutor<Order> {
    @Query("select o from Order o join fetch o.user where o.user.id = :userId and o.status <> 'DELETED'")
    List<Order> findAllOrdersByUserId(Integer userId);


}