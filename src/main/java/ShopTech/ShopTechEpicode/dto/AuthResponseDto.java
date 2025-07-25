package ShopTech.ShopTechEpicode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String message;
    private String token;
    private Set<String> roles; // aggiunto per restituire i ruoli
}
