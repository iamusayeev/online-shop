package az.online.shop.mapper;

import az.online.shop.dto.OrderDTO;
import az.online.shop.entity.Order;
import az.online.shop.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderDTO> {

    private final UserReadMapper userReadMapper;

    @Override
    public OrderDTO map(Order object) {
        return OrderDTO.builder()
                .id(object.getId())
                .address(object.getAddress())
                .status(object.getStatus())
                .sum(object.getSum())
                .userReadDto(userReadMapper.map(object.getUser()))
                .build();
    }

    @Override
    public Order mapDtoToEntity(OrderDTO dto) {
        return Order.builder()
                .address(dto.getAddress())
                .sum(dto.getSum())
                .status(Status.NEW)
                .build();
    }
}