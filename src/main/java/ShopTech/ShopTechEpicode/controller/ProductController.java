package ShopTech.ShopTechEpicode.controller;

import ShopTech.ShopTechEpicode.dto.ProductResponseDto;
import ShopTech.ShopTechEpicode.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ShopTech.ShopTechEpicode.service.ProductService;

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

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // GET single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST create product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product saved = productService.save(product);
        return ResponseEntity.ok(saved);

    }

    // PUT update product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
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

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
