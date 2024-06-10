package erp.pcpartsbackend.services;

import erp.pcpartsbackend.controllers.dto.ProductDto;
import erp.pcpartsbackend.models.Product;
import erp.pcpartsbackend.models.Provider;
import erp.pcpartsbackend.repositories.ProductRepository;
import erp.pcpartsbackend.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProviderRepository providerRepository;

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

    public List<Product> getProductsByProvider(String providerName) {
        return productRepository.findProductsByProvider_providerName(providerName);
    }

    public List<Product> getProductsByPriceRange(Float priceMin, Float priceMax) {
        return productRepository.findProductsByProductPriceBetween(priceMin, priceMax);
    }

    public Product addProduct(ProductDto productDto) {
        Product existingProduct = productRepository.findProductByProductId(productDto.getProductId());

        if (existingProduct != null) {
            // Update existing product
            existingProduct.setProductName(productDto.getProductName());
            existingProduct.setProductPrice(productDto.getProductPrice());
            existingProduct.setProductCategory(productDto.getProductCategory());
            existingProduct.setQuantityInStock(productDto.getQuantityInStock());
            existingProduct.setImageUrl(productDto.getImageUrl());

            Provider provider = providerRepository.findProviderByUserId(productDto.getProviderId());
            existingProduct.setProvider(provider);

            return productRepository.save(existingProduct);
        } else {
            // Create new product
            Product newProduct = new Product();
            newProduct.setProductName(productDto.getProductName());
            newProduct.setProductPrice(productDto.getProductPrice());
            newProduct.setProductCategory(productDto.getProductCategory());
            newProduct.setQuantityInStock(productDto.getQuantityInStock());
            newProduct.setImageUrl(productDto.getImageUrl());

            Provider provider = providerRepository.findProviderByUserId(productDto.getProviderId());
            newProduct.setProvider(provider);

            return productRepository.save(newProduct);
        }
    }
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    public boolean existById(UUID productId){
        return getProductById(productId) != null;
    }
}
