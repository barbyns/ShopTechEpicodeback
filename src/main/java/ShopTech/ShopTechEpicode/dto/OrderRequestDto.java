package ShopTech.ShopTechEpicode.dto;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {
    private List <OrderItemDto> orderItems;

    public OrderItemDto[] getItems() {
        return new OrderItemDto[0];
    }
}

