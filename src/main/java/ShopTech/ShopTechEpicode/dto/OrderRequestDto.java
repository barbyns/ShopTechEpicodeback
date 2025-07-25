package ShopTech.ShopTechEpicode.dto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List <OrderItemDto> items;
}
