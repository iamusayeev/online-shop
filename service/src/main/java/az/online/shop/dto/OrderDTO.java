package az.online.shop.dto;

import az.online.shop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderDTO {
    Integer id;
    String address;
    BigDecimal sum;
    Status status;
    LocalDateTime createdAt;
    UserReadDTO userReadDto;
}