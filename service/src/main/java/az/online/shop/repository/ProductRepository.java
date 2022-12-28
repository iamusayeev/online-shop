package az.online.shop.repository;

import az.online.shop.entity.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p  join fetch p.categories c join fetch c.category")
    List<Product> findAllWithCategories();
}