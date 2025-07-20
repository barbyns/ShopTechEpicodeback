package service;
import model.User;
import repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean existsByEmail (String email){
        return userRepository.existsByEmail(email);
    }
    public void save (User user){
        userRepository.save(user);
    }

    public User findByEmail(String username) {
    }
}
