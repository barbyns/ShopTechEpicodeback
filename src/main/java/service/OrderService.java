package service;

import dto.OrderItemDto;
import dto.OrderRequestDto;
import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.OrderRepository;
import repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder(User utente, OrderRequestDto orderDto) {
        Order ordine = new Order();
        ordine.setUtente(utente);
        ordine.setDataOrdine(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        double totale = 0;

        for (OrderItemDto itemDto : orderDto.getItems()) {
            Product prodotto = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Prodotto non trovato con ID: " + itemDto.getProductId()));

            OrderItem item = new OrderItem();
            item.setProdotto(prodotto);
            item.setQuantita(itemDto.getQuantita());
            item.setPrezzo(prodotto.getPrezzo());
            item.setOrdine(ordine);

            items.add(item);
            totale += prodotto.getPrezzo() * itemDto.getQuantita();
        }

        ordine.setItems(items);
        ordine.setTotale(totale);

        return orderRepository.save(ordine);
    }

    public List<Order> getOrdersByUser(User utente) {
        return orderRepository.findByUtente(utente);
    }
}
