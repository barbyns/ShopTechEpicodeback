package controller;

import dto.OrderRequestDto;
import dto.OrderResponseDto;
import model.Order;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import service.OrderService;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(Authentication auth, @RequestBody OrderRequestDto orderDto) {
        User user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        OrderResponseDto ordineDto = orderService.createOrder(user, orderDto);
        return ResponseEntity.ok(ordineDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(Authentication auth) {
        User user = userService.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        List<OrderResponseDto> ordini = orderService.getOrdersByUser(user);
        return ResponseEntity.ok(ordini);
    }
}
