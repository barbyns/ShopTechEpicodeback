
package ShopTech.ShopTechEpicode.dto;

import jakarta.validation.constraints.*;

public class ProductRequestDto {

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "La descrizione è obbligatoria")
    private String descrizione;

    @Positive(message = "Il prezzo deve essere positivo")
    private double prezzo;

    @PositiveOrZero(message = "La quantità deve essere positiva o zero")
    private int quantita;

    @NotNull(message = "La categoria è obbligatoria")
    private Long categoriaId;

    // Getters e Setters
}
