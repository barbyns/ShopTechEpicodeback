package ShopTech.ShopTechEpicode.service;
import ShopTech.ShopTechEpicode.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ShopTech.ShopTechEpicode.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;

@Service

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }
    public Optional<Category> findById(Long id){
        return categoryRepository.findById(id);
    }
    public Category save(Category category){
        return categoryRepository.save(category);
    }
    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
