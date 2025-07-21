package controller;

import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication authentication) {
        String email = authentication.getName(); // JWT contiene l'email come subject
        Optional<User> user = userService.findByEmail(email);

        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
