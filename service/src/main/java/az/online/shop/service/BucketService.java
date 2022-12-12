package az.online.shop.service;

import az.online.shop.dto.BucketDTO;
import az.online.shop.dto.BucketDetailDTO;
import az.online.shop.entity.Bucket;
import az.online.shop.entity.Product;
import az.online.shop.entity.User;
import az.online.shop.repository.BucketRepository;
import az.online.shop.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BucketService {

    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public Bucket createBucket(User user, List<Integer> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        var productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }


    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByUsername(name);
        if (user == null || user.getBucket() == null) {
            return new BucketDTO();
        }

        BucketDTO bucketDTO = new BucketDTO();
        Map<Integer, BucketDetailDTO> mapByProductId = new HashMap<>();

        Integer bucketId = user.getBucket().getId();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new BucketDetailDTO(product, bucketId));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.parseDouble(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    @Transactional
    public void addProducts(Bucket bucket, List<Integer> productIds) {
        var products = bucket.getProducts();
        List<Product> newProductList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }


    @Transactional
    public boolean delete(Integer id) {
        return bucketRepository.findById(id)
                .map(bucket -> {
                    bucketRepository.deleteById(id);
                    bucketRepository.flush();
                    return true;
                }).orElse(false);
    }

    private List<Product> getCollectRefProductsByIds(List<Integer> productsId) {
        return productsId.stream()
                .map(productRepository::getReferenceById).toList();
    }
}