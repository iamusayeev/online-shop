package az.online.shop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductReadDTO {
    Integer id;
    String title;
    BigDecimal price;
    String image;
}