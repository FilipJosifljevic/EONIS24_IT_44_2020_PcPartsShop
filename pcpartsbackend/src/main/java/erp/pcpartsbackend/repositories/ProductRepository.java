package erp.pcpartsbackend.repositories;

import erp.pcpartsbackend.models.Product;
import erp.pcpartsbackend.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findProductByProductName(String productName);
    List<Product> findProductByProductNameContaining(String productName);
    List<Product> findProductsByProductCategory(String productCategory);
    List<Product> findProductsByProvider_providerName(String providerName);
    List<Product> findProductsByProductPriceBetween(Float minPrice, Float maxPrice);
    Product findProductByProductId(UUID productId);
}
