package az.online.shop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class ProductCreateDTO {
    Integer id;
    String title;
    BigDecimal price;
    MultipartFile image;
}