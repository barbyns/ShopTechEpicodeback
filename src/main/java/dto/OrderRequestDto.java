package dto;
import lombok.Data;
import model.OrderItem;

import java.util.List;

@Data
public class OrderRequestDto {
    private List <OrderItemDto> items;
}
