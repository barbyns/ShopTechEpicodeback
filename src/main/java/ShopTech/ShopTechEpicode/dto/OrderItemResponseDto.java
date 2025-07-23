package ShopTech.ShopTechEpicode.dto;
import lombok.Data;

@Data
public class OrderItemResponseDto {
    private Long productId;
    private String nomeProdotto;
    private int quantita;
    private Double prezzo;

}
