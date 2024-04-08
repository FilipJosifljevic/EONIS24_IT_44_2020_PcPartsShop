package erp.pcpartsbackend.services;

import erp.pcpartsbackend.models.Product;
import erp.pcpartsbackend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID productId) {
        return productRepository.findProductByProductId(productId);
    }

    public List<Product> getProductsByCategory(String productCategory) {
        return productRepository.findProductsByProductCategory(productCategory);
    }

    public List<Product> getProductsByName(String productName) {
        return  productRepository.findProductByProductNameContaining(productName);
    }

    public List<Product> getProductsByProvider(UUID providerId) {
        return productRepository.findProductsByProvider_UserId(providerId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}
