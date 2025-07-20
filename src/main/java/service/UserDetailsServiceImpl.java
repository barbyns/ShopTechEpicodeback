package service;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
public interface UserDetailsServiceImpl extends JpaRepository<User, Integer>, UserDetailsService {
    public Optional<User> findByEmail(String email);
    boolean existsByEmail (String email);
}
