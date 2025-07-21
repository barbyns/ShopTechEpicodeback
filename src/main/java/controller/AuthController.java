package controller;

import dto.AuthResponseDto;
import dto.LoginDto;
import dto.RegisterDto;
import jakarta.validation.Valid;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ REGISTRAZIONE
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterDto registerDto) {
        if (userService.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body(
                    new AuthResponseDto("Email già registrata.", null)
            );
        }

        User user = new User();
        user.setNome(registerDto.getNome());           // Usa il nome corretto del campo
        user.setCognome(registerDto.getCognome());     // Usa il nome corretto del campo
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRuolo("USER");

        userService.save(user);

        return ResponseEntity.ok(new AuthResponseDto("Registrazione avvenuta con successo!", null));
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
