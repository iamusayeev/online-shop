package az.online.shop.service;

import az.online.shop.dto.ProductCreateDTO;
import az.online.shop.dto.ProductReadDTO;
import az.online.shop.entity.Product;
import az.online.shop.entity.User;
import az.online.shop.mapper.ProductMapper;
import az.online.shop.mapper.ProductReadMapper;
import az.online.shop.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    private final ProductMapper productMapper;
    private final ImageService imageService;
    private final ProductReadMapper productReadMapper;

    public List<ProductReadDTO> getAll() {
        return productRepository.findAll().stream()
                .map(productReadMapper::map)
                .toList();
    }

    public void addToUserBucket(List<Integer> productIds, String username) {
        User user = userService.findByUsername(username);
        if (user.getBucket() == null) {
            bucketService.createBucket(user, productIds);
        } else {
            bucketService.addProducts(user.getBucket(), productIds);
        }
    }

    public void addProduct(ProductCreateDTO productDTO) {
        Product product = productMapper.mapDtoToEntity(productDTO);
        productRepository.save(product);
    }


    public Optional<byte[]> findAvatar(Integer id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}