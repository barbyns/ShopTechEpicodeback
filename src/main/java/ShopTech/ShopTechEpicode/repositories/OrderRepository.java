package ShopTech.ShopTechEpicode.repositories;
import ShopTech.ShopTechEpicode.model.Order;
import ShopTech.ShopTechEpicode.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUtente(User utente);


}
