package ShopTech.ShopTechEpicode.controller;

import ShopTech.ShopTechEpicode.model.Product;
import ShopTech.ShopTechEpicode.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ Accessibile a tutti
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // ✅ Accessibile a tutti
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product saved = productService.save(product);
        return ResponseEntity.ok(saved);
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setNome(productDetails.getNome());
            product.setDescrizione(productDetails.getDescrizione());
            product.setPrezzo(productDetails.getPrezzo());
            product.setQuantity(productDetails.getQuantity());
            product.setImmagineUrl(productDetails.getImmagineUrl());

            return ResponseEntity.ok(productService.save(product));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Solo ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isPresent()) {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
