package ShopTech.ShopTechEpicode.dto;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderResponseDto {
    private Long id;
    private LocalDateTime dataOrdine;
    private List<OrderItemResponseDto> items;
    private double totale;
}
