package az.online.shop.http.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import az.online.shop.dto.OrderDTO;
import az.online.shop.dto.OrderFilter;
import az.online.shop.dto.PageResponse;
import az.online.shop.model.Status;
import az.online.shop.service.OrderService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String findAll(Model model,
                          OrderFilter filter,
                          Pageable pageable) {
        model.addAttribute("filter", filter);
        Page<OrderDTO> page = orderService.findAll(filter, pageable);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("orders", PageResponse.of(page));
        return "order/orders";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("statuses", Status.values());
                    return "order/order";
                }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @GetMapping("findAllByUser")
    public String findAllByUsername(Model model,
                                    Principal principal) {
        model.addAttribute("orders", orderService.findAllOrdersByUsername(principal.getName()));
        return "order/userOrders";
    }


    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, OrderDTO order) {
        boolean updated = orderService.update(id, order);
        if (updated) {
            return "redirect:/orders/{id}";
        }
        throw new ResponseStatusException(NOT_FOUND);
    }


    @PostMapping
    public String create(Model model, OrderDTO orderDto, Principal principal) {
        model.addAttribute("order", orderDto);
        orderService.addOrder(orderDto, principal);
        return "redirect:/buckets";
    }
}