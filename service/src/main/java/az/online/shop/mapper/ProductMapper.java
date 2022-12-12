package az.online.shop.mapper;

import static java.util.function.Predicate.not;

import az.online.shop.dto.ProductCreateDTO;
import az.online.shop.entity.Product;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ProductMapper implements Mapper<Product, ProductCreateDTO> {

    @Override
    public ProductCreateDTO map(Product object) {
        return ProductCreateDTO.builder()
                .price(object.getPrice())
                .title(object.getTitle())
                .id(object.getId())
                .build();
    }

    @Override
    public Product mapDtoToEntity(ProductCreateDTO dto) {
        Product product = Product.builder()
                .price(dto.getPrice())
                .title(dto.getTitle())
                .build();

        Optional.ofNullable(dto.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> product.setImage(image.getOriginalFilename()));
        return product;
    }
}