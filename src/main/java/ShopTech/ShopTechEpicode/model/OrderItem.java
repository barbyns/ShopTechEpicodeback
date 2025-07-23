package ShopTech.ShopTechEpicode.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantita;
    private Double prezzo;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product prodotto;

    @ManyToOne
    @JoinColumn
    private Order ordine;
}
