package ShopTech.ShopTechEpicode.service;

import ShopTech.ShopTechEpicode.dto.OrderItemDto;
import ShopTech.ShopTechEpicode.dto.OrderItemResponseDto;
import ShopTech.ShopTechEpicode.dto.OrderRequestDto;
import ShopTech.ShopTechEpicode.dto.OrderResponseDto;
import ShopTech.ShopTechEpicode.model.Order;
import ShopTech.ShopTechEpicode.model.OrderItem;
import ShopTech.ShopTechEpicode.model.Product;
import ShopTech.ShopTechEpicode.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ShopTech.ShopTechEpicode.repositories.OrderRepository;
import ShopTech.ShopTechEpicode.repositories.ProductRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderResponseDto createOrder(User utente, OrderRequestDto orderDto) {
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

        Order ordineSalvato = orderRepository.save(ordine);

        return convertToDto(ordineSalvato);
    }

    public List<OrderResponseDto> getOrdersByUser(User utente) {
        List<Order> ordini = orderRepository.findByUtente(utente);
        return ordini.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderResponseDto convertToDto(Order ordine) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(ordine.getId());
        dto.setDataOrdine(ordine.getDataOrdine());
        dto.setTotale(ordine.getTotale());

        List<OrderItemResponseDto> itemDtos = ordine.getItems().stream().map(item -> {
            OrderItemResponseDto itemDto = new OrderItemResponseDto();
            itemDto.setProductId(item.getProdotto().getId());
            itemDto.setNomeProdotto(item.getProdotto().getNome());
            itemDto.setQuantita(item.getQuantita());
            itemDto.setPrezzo(item.getPrezzo());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}
