package az.online.shop.repository;

import az.online.shop.entity.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Integer> {
}