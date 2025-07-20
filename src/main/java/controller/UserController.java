package controller;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import lombok.RequiredArgsConstructor;


    @RestController
    @RequestMapping("/api/user")
    @RequiredArgsConstructor
    public class UserController {

        private final UserService userService;

        @GetMapping("/me")
        public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
            if (userDetails == null) {
                return ResponseEntity.status(401).body("Utente non autenticato");
            }

            // Recupera l'utente dal database usando l'email
            User user = userService.findByEmail(userDetails.getUsername());

            if (user == null) {
                return ResponseEntity.status(404).body("Utente non trovato");
            }

            return ResponseEntity.ok(user);
        }
    }
