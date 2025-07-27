package ShopTech.ShopTechEpicode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private LocalDateTime dataOrdine;
    private List<OrderItemResponseDto> items = new ArrayList<>();
    private double totale;
}
