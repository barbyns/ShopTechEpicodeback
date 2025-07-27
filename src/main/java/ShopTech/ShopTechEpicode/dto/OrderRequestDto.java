package ShopTech.ShopTechEpicode.dto;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    @NotEmpty(message = "L'ordine non può essere vuoto")
    private List <OrderItemDto> items;
}
