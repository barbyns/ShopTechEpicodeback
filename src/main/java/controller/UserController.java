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
  //Get user
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    //Put
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updateUser){
        return userService.findById(id).map(user -> {
            user.setNome(updateUser.getNome());
            user.setCognome(updateUser.getCognome());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());
            return ResponseEntity.ok(userService.save(user));
        }).orElse(ResponseEntity.notFound().build());
        }
        // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        if(userService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<User> getMe(Authentication authentication) {
        String email = authentication.getName(); // recuperato dal JWT
        User user = userService.getByEmailOrThrow(email);
        return ResponseEntity.ok(user);
    }
}
