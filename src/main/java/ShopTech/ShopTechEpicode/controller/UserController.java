package ShopTech.ShopTechEpicode.controller;

import ShopTech.ShopTechEpicode.dto.UserRequestDto;
import ShopTech.ShopTechEpicode.model.User;
import ShopTech.ShopTechEpicode.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 Ottieni utente per ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Modifica utente
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody UserRequestDto updateDto) {
        return userService.findById(id)
                .map(user -> {
                    user.setNome(updateDto.getNome());
                    user.setCognome(updateDto.getCognome());
                    user.setEmail(updateDto.getEmail());
                    user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
                    return ResponseEntity.ok(userService.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Elimina utente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return userService.findById(id)
                .<ResponseEntity<Void>>map(user -> {
                    userService.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Ottieni profilo utente loggato
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        String email = authentication.getName();  // estrai email/username dal token JWT
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Crea utente
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRequestDto dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setCognome(dto.getCognome());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }
}
