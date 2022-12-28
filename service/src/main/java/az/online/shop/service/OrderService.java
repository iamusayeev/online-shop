package az.online.shop.service;

import az.online.shop.dto.OrderDTO;
import az.online.shop.dto.OrderFilter;
import az.online.shop.entity.Order;
import az.online.shop.entity.QOrder;
import az.online.shop.entity.User;
import az.online.shop.mapper.OrderMapper;
import az.online.shop.repository.OrderRepository;
import az.online.shop.repository.QPredicates;
import com.querydsl.core.types.Predicate;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final UserService userService;
    private final BucketService bucketService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Page<OrderDTO> findAll(OrderFilter filter, Pageable pageable) {
        Predicate predicate = QPredicates.builder().add(filter.address(), s -> QOrder.order.address.like("%" + s + "%"))
                .add(filter.status(), QOrder.order.status::eq)
                .add(filter.sum(), QOrder.order.sum::gt).build();

        return orderRepository.findAll(predicate, pageable)
                .map(orderMapper::map);
    }

    public List<OrderDTO> findAllOrdersByUsername(String username) {
        User user = userService.findByUsername(username);
        Integer userId = user.getId();
        return orderRepository.findAllOrdersByUserId(userId).stream()
                .map(orderMapper::map)
                .toList();
    }

    public Optional<OrderDTO> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::map);
    }

    @Transactional
    public void addOrder(OrderDTO orderDto, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Order order = orderMapper.mapDtoToEntity(orderDto);
        order.setUser(user);
        bucketService.delete(user.getBucket().getId());
        orderRepository.save(order);
    }

    @Transactional
    public boolean update(Integer id, OrderDTO orderDto) {
        Optional<Order> maybeOrder = orderRepository.findById(id);
        if (maybeOrder.isPresent()) {
            maybeOrder.get().setStatus(orderDto.getStatus());
            orderRepository.flush();
            return true;
        }
        return false;
    }
}