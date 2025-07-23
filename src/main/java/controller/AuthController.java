package controller;

import dto.AuthResponseDto;
import dto.LoginDto;
import dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import service.UserService;
import util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterDto registerDto) {
        if (userService.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new AuthResponseDto("Email già registrata", null));
        }

        User user = User.builder()
                .nome(registerDto.getNome())
                .cognome(registerDto.getCognome())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        user.setRuolo("USER");
        userService.save(user);

        return ResponseEntity.ok(new AuthResponseDto("Registrazione completata", null));
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

        String jwt = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponseDto("Login effettuato con successo", jwt));
    }
}
