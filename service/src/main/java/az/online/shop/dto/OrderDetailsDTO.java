package az.online.shop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDetailsDTO {
    BigDecimal amount;
    BigDecimal price;
}