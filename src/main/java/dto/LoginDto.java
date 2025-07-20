package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

    @NotEmpty(message = "L'email non può essere vuota")
    @Email(message = "Formato email non valido")
    private String email;

    @NotEmpty(message = "La password non può essere vuota")
    private String password;
}
