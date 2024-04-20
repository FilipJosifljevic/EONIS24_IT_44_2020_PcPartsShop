package erp.pcpartsbackend.services;

import erp.pcpartsbackend.models.ProductOrder;
import erp.pcpartsbackend.repositories.ProductOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public List<ProductOrder> getAllProductOrders() {
        return productOrderRepository.findAll();
    }

    public ProductOrder getProductOrderById(UUID productOrderId) {
        return productOrderRepository.findByProductOrderId(productOrderId);
    }

    public ProductOrder addProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public void deleteProductOrder(ProductOrder productOrder){
        productOrderRepository.delete(productOrder);
    }

    public boolean existById(UUID productOrderId){
        return getProductOrderById(productOrderId) != null;
    }
}
