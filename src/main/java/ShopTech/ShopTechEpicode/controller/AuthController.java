package ShopTech.ShopTechEpicode.controller;

import ShopTech.ShopTechEpicode.dto.AuthResponseDto;
import ShopTech.ShopTechEpicode.dto.LoginDto;
import ShopTech.ShopTechEpicode.dto.RegisterDto;
import ShopTech.ShopTechEpicode.model.User;
import ShopTech.ShopTechEpicode.service.UserService;
import ShopTech.ShopTechEpicode.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // ✅ REGISTRAZIONE
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterDto registerDto) {
        if (userService.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponseDto("Email già registrata", null, null));
        }

        User user = User.builder()
                .nome(registerDto.getNome())
                .cognome(registerDto.getCognome())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        // ✅ Imposta ruolo di default
        user.setRuolo("USER");
        userService.save(user);

        return ResponseEntity.ok(
                new AuthResponseDto("Registrazione completata", null, user.getRuoli())
        );
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        User user = userService.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String jwt = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                new AuthResponseDto("Login effettuato con successo", jwt, user.getRuoli())
        );
    }
}
