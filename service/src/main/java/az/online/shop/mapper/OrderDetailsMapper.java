package az.online.shop.mapper;

import az.online.shop.dto.OrderDetailsDTO;
import az.online.shop.entity.OrdersDetails;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class OrderDetailsMapper implements Mapper<OrdersDetails, OrderDetailsDTO> {

    @Override
    public OrderDetailsDTO map(OrdersDetails object) {
        return OrderDetailsDTO.builder()
                .amount(object.getAmount())
                .price(object.getPrice())
                .build();
    }

    @Override
    public OrdersDetails mapDtoToEntity(OrderDetailsDTO dto) {
        return OrdersDetails.builder()
                .amount(dto.getAmount())
                .price(dto.getPrice())
                .build();
    }

    @Override
    public List<OrderDetailsDTO> mapEntitiesToDtos(List<OrdersDetails> entities) {
        return entities.stream().map(this::map).toList();
    }
}