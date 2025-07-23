package controller;

import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ PUT user update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User updateUser) {
        return userService.findById(id).map(user -> {
            user.setNome(updateUser.getNome());
            user.setCognome(updateUser.getCognome());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());
            return ResponseEntity.ok(userService.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<Void> deleteUs;
}
