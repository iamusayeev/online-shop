package az.online.shop.mapper;

import az.online.shop.dto.ProductReadDTO;
import az.online.shop.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductReadMapper implements Mapper<Product, ProductReadDTO> {

    @Override
    public ProductReadDTO map(Product object) {
        return ProductReadDTO.builder()
                .id(object.getId())
                .image(object.getImage())
                .title(object.getTitle())
                .price(object.getPrice())
                .build();
    }
}