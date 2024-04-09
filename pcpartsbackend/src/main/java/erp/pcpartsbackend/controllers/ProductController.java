package erp.pcpartsbackend.controllers;


import erp.pcpartsbackend.models.Product;
import erp.pcpartsbackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(
                    "Products not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return new ResponseEntity<>(
                    "Product not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("products")
    public ResponseEntity<?> addproduct(@RequestBody Product product) {
        if(productService.existById(product.getProductId())){
            return new ResponseEntity<>(
                    "Product with that id already exists",
                    HttpStatus.CONFLICT);
        }
        Product savedproduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(savedproduct);
    }

    @PutMapping("products/{id}")
    public ResponseEntity<?> updateproduct(@RequestBody Product product, @PathVariable("id") UUID productId) {
        product.setProductId(productId);
        if(!productService.existById(product.getProductId())){
            return new ResponseEntity<>(
                    "Product with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        Product savedproduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(savedproduct);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID productId) {
        if(!productService.existById(productId)){
            return new ResponseEntity<>(
                    "Product with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        productService.deleteProduct(productService.getProductById(productId));
        return ResponseEntity.status(HttpStatus.OK).body("Product with that id has been deleted");
    }
}
