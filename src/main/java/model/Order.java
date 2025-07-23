package model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataOrdine;

    private Double totale;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User utente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}
