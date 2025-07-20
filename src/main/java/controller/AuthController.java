package controller;

import dto.RegisterDto;
import jakarta.validation.Valid;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto registerDto) {
        // Verifica se l'email è già registrata
        if (userService.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email già registrata.");
        }

        // Crea un nuovo utente
        User user = new User();
        user.setNome(registerDto.getNome());
        user.setCognome(registerDto.getCognome());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRuolo("USER");

        // Salva l'utente
        userService.save(user);

        return ResponseEntity.ok("Registrazione avvenuta con successo!");
    }
}
