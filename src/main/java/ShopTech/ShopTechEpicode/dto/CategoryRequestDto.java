
package ShopTech.ShopTechEpicode.dto;

import jakarta.validation.constraints.*;

public class CategoryRequestDto {

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    private String descrizione;

    // Getters e Setters
}
