package ShopTech.ShopTechEpicode.repositories;
import ShopTech.ShopTechEpicode.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository  extends JpaRepository<Category, Long>{
}
